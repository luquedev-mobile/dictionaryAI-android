package com.devluque.data

import com.devluque.data.datasource.AiRemoteDataSource
import com.devluque.domain.Result
import com.devluque.domain.WordDetailItem
import com.devluque.domain.remote.worddetail.RemoteWordDetailRequest
import kotlinx.coroutines.flow.Flow

class AiRepository(
    private val aiRemoteDataSource: AiRemoteDataSource
) {
    fun generateWordDetail(
        remoteWordDetailRequest: RemoteWordDetailRequest
    ): Flow<Result<WordDetailItem>> = aiRemoteDataSource.generateWordDetail(remoteWordDetailRequest)
}