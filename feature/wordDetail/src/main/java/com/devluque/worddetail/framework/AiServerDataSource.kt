package com.devluque.worddetail.framework

import com.devluque.domain.Result
import com.devluque.core.network.resultRequest
import com.devluque.data.datasource.AiRemoteDataSource
import com.devluque.domain.isSuccess
import com.devluque.worddetail.framework.generateContent.GenerateContentResponse
import com.devluque.domain.onFailure
import com.devluque.entities.Meaning
import com.devluque.entities.RemoteWordDetailRequest
import com.devluque.entities.WordDetailItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.flow
import kotlinx.serialization.json.Json

internal class AiServerDataSource(
    private val aiService: AiService
) : AiRemoteDataSource {
    override fun generateWordDetail(
        remoteWordDetailRequest: RemoteWordDetailRequest
    ): Flow<Result<WordDetailItem>> = flow {
        safetyCall(
            apiCall = {
                aiService
                    .generateWordDetail(
                        remoteWordDetailRequest = remoteWordDetailRequest
                    )
                    .resultRequest()
            },
            transform = {
                it.convertTextToSpecificType<RemoteWordDetailItem>().toResultDomain()
            }
        )
    }
}

private fun Result<RemoteWordDetailItem>.toResultDomain(): Result<WordDetailItem> {
    return map { it.toDomain() }
}

private fun RemoteWordDetailItem.toDomain(): WordDetailItem {
    return WordDetailItem(
        meanings = this.meanings?.map { it.toDomain() },
        word = this.word
    )
}

private fun RemoteMeaning.toDomain(): Meaning {
    return Meaning(
        exampleEnglish = this.exampleEnglish,
        exampleSpanish = this.exampleSpanish,
        explanation = this.explanation,
        mean = this.mean,
    )
}

suspend inline fun <T, R> FlowCollector<Result<R>>.safetyCall(
    apiCall: () -> Result<T>,
    noinline transform: ((T) -> Result<R>)? = null
) {
    try {
        val response = apiCall()
        response.isSuccess { data ->
            if (transform != null) {
                emit(transform(data))
            } else {
                @Suppress("UNCHECKED_CAST")
                emit(Result.Success(data as R))
            }
        }.onFailure {
            emit(Result.Error(it))
        }
    } catch (e: Exception) {
        emit(Result.Error(e))
    }
}

private inline fun <reified T> GenerateContentResponse.convertTextToSpecificType(): Result<T> {
    return try {
        Result.Success(
            Json.decodeFromString<T>(
                this.candidates[0].content.parts[0].text
            )
        )
    } catch (e: Exception) {
        Result.Error(e)
    }
}