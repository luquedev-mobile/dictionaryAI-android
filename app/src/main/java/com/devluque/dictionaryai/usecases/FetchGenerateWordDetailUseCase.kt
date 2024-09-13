package com.devluque.dictionaryai.usecases

import com.devluque.dictionaryai.Result
import com.devluque.dictionaryai.data.AiRepository
import com.devluque.dictionaryai.framework.remote.wordDetail.WordDetailRequest
import com.devluque.dictionaryai.domain.WordDetailItem
import kotlinx.coroutines.flow.Flow

class FetchGenerateWordDetailUseCase(private val aiRepository: AiRepository) {
    operator fun invoke(request: WordDetailRequest): Flow<Result<WordDetailItem>> = aiRepository.generateWordDetail(request)
}