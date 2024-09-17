package com.devluque.search

import com.devluque.data.WordsRepository
import com.devluque.data.datasource.WordsLocalDataSource
import com.devluque.search.framework.WordsRoomDataSource
import com.devluque.search.ui.SearchViewModel
import com.devluque.usecases.DeleteWordUseCase
import com.devluque.usecases.GetRecentWordsUseCase
import com.devluque.usecases.InsertWordUseCase
import com.devluque.usecases.SearchWordsUseCase
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.bind
import org.koin.dsl.module

val diSearchModule = module {
    factoryOf(::WordsRoomDataSource) bind WordsLocalDataSource::class
    factoryOf(::WordsRepository)
    factoryOf(::DeleteWordUseCase)
    factoryOf(::GetRecentWordsUseCase)
    factoryOf(::InsertWordUseCase)
    factoryOf(::SearchWordsUseCase)
    viewModelOf(::SearchViewModel)
}