package com.devluque.data.datasource

import com.devluque.domain.Result
import com.devluque.domain.WordDetailItem
import com.devluque.domain.remote.worddetail.RemoteWordDetailRequest
import kotlinx.coroutines.flow.Flow

interface AiRemoteDataSource {
    fun generateWordDetail(
        remoteWordDetailRequest: RemoteWordDetailRequest
    ): Flow<Result<WordDetailItem>>
}
