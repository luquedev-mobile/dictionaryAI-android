package com.devluque.worddetail.framework

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

class AiServerDataSource(
    private val aiService: AiService
) : AiRemoteDataSource {
    override fun generateWordDetail(
        remoteWordDetailRequest: RemoteWordDetailRequest
    ): Flow<com.devluque.domain.Result<WordDetailItem>> = flow {
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

private fun com.devluque.domain.Result<RemoteWordDetailItem>.toResultDomain(): com.devluque.domain.Result<WordDetailItem> {
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

suspend inline fun <T, R> FlowCollector<com.devluque.domain.Result<R>>.safetyCall(
    apiCall: () -> com.devluque.domain.Result<T>,
    noinline transform: ((T) -> com.devluque.domain.Result<R>)? = null
) {
    try {
        val response = apiCall()
        response.isSuccess { data ->
            if (transform != null) {
                emit(transform(data))
            } else {
                @Suppress("UNCHECKED_CAST")
                emit(com.devluque.domain.Result.Success(data as R))
            }
        }.onFailure {
            emit(com.devluque.domain.Result.Error(it))
        }
    } catch (e: Exception) {
        emit(com.devluque.domain.Result.Error(e))
    }
}

private inline fun <reified T> GenerateContentResponse.convertTextToSpecificType(): com.devluque.domain.Result<T> {
    return try {
        com.devluque.domain.Result.Success(
            Json.decodeFromString<T>(
                this.candidates[0].content.parts[0].text
            )
        )
    } catch (e: Exception) {
        com.devluque.domain.Result.Error(e)
    }
}