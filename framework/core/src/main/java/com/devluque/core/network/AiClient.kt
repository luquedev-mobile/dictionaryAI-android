package com.devluque.core.network

import com.devluque.domain.Result
import kotlinx.serialization.json.Json
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.HttpException
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import java.net.SocketTimeoutException

class AiClient <T>(
    private val apiKey: String,
    apiUrl: String,
    service: Class<T>
) {
    private val okHttpClient = okhttp3.OkHttpClient.Builder()
        .addInterceptor(::apiKeyAsQuery)
        .build()

    private val json = Json {
        ignoreUnknownKeys = true
    }

    val instance: T = Retrofit.Builder()
        .baseUrl(apiUrl)
        .client(okHttpClient)
        .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
        .build()
        .create(service)

    private fun apiKeyAsQuery(chain: Interceptor.Chain) = chain.proceed(
        chain
            .request()
            .newBuilder()
            .url(
                chain.request().url
                    .newBuilder()
                    .addQueryParameter("key", apiKey)
                    .build()
            )
            .build()
    )
}


fun <T> Response<T>.resultRequest(): Result<T> {
    return try {
        if (isSuccessful) {
            body()?.let {
                Result.Success(it)
            } ?: run {
                Result.Error(NullPointerException())
            }
        } else {
            Result.Error(HttpException(this))
        }
    } catch (e: SocketTimeoutException) {
        Result.Error(e)
    } catch (e: Exception) {
        Result.Error(e)
    }
}