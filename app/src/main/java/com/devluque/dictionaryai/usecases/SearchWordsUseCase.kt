package com.devluque.dictionaryai.usecases

import com.devluque.dictionaryai.data.WordsRepository
import com.devluque.dictionaryai.domain.Word
import kotlinx.coroutines.flow.Flow

class SearchWordsUseCase(
    private val wordsRepository: WordsRepository
) {
    operator fun invoke(query: String): Flow<List<Word>> = wordsRepository.searchWords(query)
}