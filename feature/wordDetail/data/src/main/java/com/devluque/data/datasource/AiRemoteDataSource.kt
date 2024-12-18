package com.devluque.data.datasource

import com.devluque.domain.Result
import com.devluque.entities.WordDetailItem
import com.devluque.entities.RemoteWordDetailRequest
import kotlinx.coroutines.flow.Flow

interface AiRemoteDataSource {
    fun generateWordDetail(
        remoteWordDetailRequest: RemoteWordDetailRequest
    ): Flow<Result<WordDetailItem>>
}
