package com.devluque.dictionaryai.data

import com.devluque.dictionaryai.Result
import com.devluque.dictionaryai.data.datasource.AiRemoteDataSource
import com.devluque.dictionaryai.data.datasource.remote.wordDetail.WordDetailRequest
import com.devluque.dictionaryai.data.model.WordDetailItem
import kotlinx.coroutines.flow.Flow

class AiRepository(
    private val aiRemoteDataSource: AiRemoteDataSource
) {
    fun generateWordDetail(
        wordDetailRequest: WordDetailRequest
    ): Flow<Result<WordDetailItem>> = aiRemoteDataSource.generateWordDetail(wordDetailRequest)
}