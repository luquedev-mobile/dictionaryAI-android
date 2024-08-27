package com.devluque.dictionaryai.data

import com.devluque.dictionaryai.BuildConfig
import kotlinx.serialization.json.Json
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import retrofit2.create

object AiClient {
    private val okHttpClient = okhttp3.OkHttpClient.Builder()
        .addInterceptor(::apiKeyAsQuery)
        .build()

    private val json = Json {
        ignoreUnknownKeys = true
    }

    val instance = Retrofit.Builder()
        .baseUrl("https://generativelanguage.googleapis.com/v1beta/")
        .client(okHttpClient)
        .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
        .build()
        .create<AiService>()
}


private fun apiKeyAsQuery(chain: Interceptor.Chain) = chain.proceed(
    chain
        .request()
        .newBuilder()
        .url(
            chain.request().url
                .newBuilder()
                .addQueryParameter("key", BuildConfig.API_KEY)
                .build()
        )
        .build()
)