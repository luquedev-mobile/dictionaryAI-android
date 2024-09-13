package com.devluque.usecases

import com.devluque.data.WordsRepository
import com.devluque.domain.Word
import kotlinx.coroutines.flow.Flow

class SearchWordsUseCase(
    private val wordsRepository: WordsRepository
) {
    operator fun invoke(query: String): Flow<List<Word>> = wordsRepository.searchWords(query)
}