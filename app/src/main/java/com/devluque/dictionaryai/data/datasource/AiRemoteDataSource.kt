package com.devluque.dictionaryai.data.datasource

import com.devluque.dictionaryai.Result
import com.devluque.dictionaryai.data.model.WordDetailItem
import com.devluque.dictionaryai.data.datasource.remote.AiClient
import com.devluque.dictionaryai.data.datasource.remote.generateContent.GenerateContentResponse
import com.devluque.dictionaryai.data.datasource.remote.resultRequest
import com.devluque.dictionaryai.data.datasource.remote.wordDetail.WordDetailRequest
import com.devluque.dictionaryai.isSuccess
import com.devluque.dictionaryai.onFailure
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.flow
import kotlinx.serialization.json.Json

class AiRemoteDataSource {
    fun generateWordDetail(
        wordDetailRequest: WordDetailRequest
    ) = flow {
        safetyCall(
            apiCall = {
                AiClient
                    .instance
                    .generateWordDetail(
                        wordDetailRequest = wordDetailRequest
                    )
                    .resultRequest()
            },
            transform = { it.convertTextToSpecificType<WordDetailItem>() }
        )
    }
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