package com.devluque.dictionaryai.data.datasource.remote

import com.devluque.dictionaryai.data.datasource.remote.generateContent.GenerateContentResponse
import com.devluque.dictionaryai.data.datasource.remote.wordDetail.WordDetailRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Path

interface AiService {
    @POST("models/{model}:generateContent")
    suspend fun generateWordDetail(
        @Path("model") model: String = "gemini-1.5-flash-latest",
        @Body wordDetailRequest: WordDetailRequest
    ): Response<GenerateContentResponse>
}