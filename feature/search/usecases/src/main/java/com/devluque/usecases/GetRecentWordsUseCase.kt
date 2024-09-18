package com.devluque.usecases

import com.devluque.data.WordsRepository
import com.devluque.entities.Word
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetRecentWordsUseCase @Inject constructor(
    private val wordsRepository: WordsRepository
) {
    operator fun invoke(): Flow<List<Word>> = wordsRepository.getRecentWords
}