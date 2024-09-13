package com.devluque.dictionaryai.usecases

import com.devluque.dictionaryai.data.WordsRepository
import com.devluque.dictionaryai.domain.Word
import kotlinx.coroutines.flow.Flow

class GetRecentWordsUseCase(
    private val wordsRepository: WordsRepository
) {
    operator fun invoke(): Flow<List<Word>> = wordsRepository.getRecentWords
}