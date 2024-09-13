package com.devluque.dictionaryai.framework.remote

import com.devluque.dictionaryai.framework.remote.generateContent.GenerateContentResponse
import com.devluque.domain.remote.worddetail.RemoteWordDetailRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Path

interface AiService {
    @POST("models/{model}:generateContent")
    suspend fun generateWordDetail(
        @Path("model") model: String = "gemini-1.5-flash-latest",
        @Body remoteWordDetailRequest: RemoteWordDetailRequest
    ): Response<GenerateContentResponse>
}