package com.devluque.worddetail.framework

import com.devluque.core.network.AiClient
import com.devluque.data.datasource.AiRemoteDataSource
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Named

@Module
@InstallIn(SingletonComponent::class)
internal abstract class DataSourceModule {
    @Binds
    abstract fun bindAiRemoteDataSource(aiServerDataSource: AiServerDataSource): AiRemoteDataSource
}

@Module
@InstallIn(SingletonComponent::class)
object AiClientModule {
    @Provides
    fun provideAiClient(
        @Named("apiKey") apiKey: String,
        @Named("apiUrl") apiUrl: String
    ): AiService = AiClient(
        apiKey,
        apiUrl,
        AiService::class.java
    ).instance
}
