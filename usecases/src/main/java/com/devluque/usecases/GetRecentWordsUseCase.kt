package com.devluque.usecases

import com.devluque.data.WordsRepository
import com.devluque.domain.Word
import kotlinx.coroutines.flow.Flow

class GetRecentWordsUseCase(
    private val wordsRepository: WordsRepository
) {
    operator fun invoke(): Flow<List<Word>> = wordsRepository.getRecentWords
}