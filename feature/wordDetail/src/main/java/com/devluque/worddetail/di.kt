package com.devluque.worddetail

import com.devluque.core.network.AiClient
import com.devluque.data.AiRepository
import com.devluque.data.datasource.AiRemoteDataSource
import com.devluque.usecases.FetchGenerateWordDetailUseCase
import com.devluque.worddetail.framework.AiServerDataSource
import com.devluque.worddetail.framework.AiService
import com.devluque.worddetail.ui.WordDetailViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.dsl.factoryOf
import org.koin.core.qualifier.named
import org.koin.dsl.bind
import org.koin.dsl.module

val diWordDetailModule = module {
    factoryOf(::AiServerDataSource) bind AiRemoteDataSource::class
    factoryOf(::AiRepository)
    factoryOf(::FetchGenerateWordDetailUseCase)
    viewModelOf(::WordDetailViewModel)

    // Define AiService directamente
    single { AiService::class.java }

    factory {
        AiClient(
            apiKey = get(named("apiKey")),
            service = get<Class<AiService>>()
        ).instance
    }
}