package com.devluque.usecases

import com.devluque.data.AiRepository
import com.devluque.domain.Result
import com.devluque.entities.WordDetailItem
import com.devluque.entities.RemoteWordDetailRequest
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FetchGenerateWordDetailUseCase @Inject constructor(private val aiRepository: AiRepository) {
    operator fun invoke(request: RemoteWordDetailRequest): Flow<Result<WordDetailItem>> = aiRepository.generateWordDetail(request)
}