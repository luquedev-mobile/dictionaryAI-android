package com.devluque.dictionaryai.data.datasource

import com.devluque.dictionaryai.Result
import com.devluque.dictionaryai.framework.remote.wordDetail.WordDetailRequest
import com.devluque.dictionaryai.domain.WordDetailItem
import kotlinx.coroutines.flow.Flow

interface AiRemoteDataSource {
    fun generateWordDetail(
        wordDetailRequest: WordDetailRequest
    ): Flow<Result<WordDetailItem>>
}
