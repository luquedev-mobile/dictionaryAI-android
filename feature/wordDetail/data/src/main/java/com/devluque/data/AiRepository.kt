package com.devluque.data

import com.devluque.data.datasource.AiRemoteDataSource
import com.devluque.domain.Result
import com.devluque.entities.WordDetailItem
import com.devluque.entities.RemoteWordDetailRequest
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AiRepository @Inject constructor(
    private val aiRemoteDataSource: AiRemoteDataSource
) {
    fun generateWordDetail(
        remoteWordDetailRequest: RemoteWordDetailRequest
    ): Flow<Result<WordDetailItem>> = aiRemoteDataSource.generateWordDetail(remoteWordDetailRequest)
}