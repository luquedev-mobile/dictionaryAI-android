package com.devluque.usecases

import com.devluque.data.WordsRepository
import javax.inject.Inject

class DeleteWordUseCase @Inject constructor(
    private val wordsRepository: WordsRepository
) {
    suspend operator fun invoke(word: String) {
        wordsRepository.deleteWord(word)
    }
}