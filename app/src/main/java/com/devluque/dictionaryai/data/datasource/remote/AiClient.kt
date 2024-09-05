package com.devluque.dictionaryai.data.datasource.remote

import com.devluque.dictionaryai.BuildConfig
import com.devluque.dictionaryai.Result
import kotlinx.serialization.json.Json
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.HttpException
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import retrofit2.create
import java.net.SocketTimeoutException

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

fun <T> Response<T>.resultRequest(): Result<T> {
    return try {
        if (isSuccessful) {
            body()?.let {
                Result.Success(it)
            }?: run {
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