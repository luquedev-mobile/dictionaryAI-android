package com.devluque.usecases

import com.devluque.data.WordsRepository

class DeleteWordUseCase(
    private val wordsRepository: WordsRepository
) {
    suspend operator fun invoke(word: String) {
        wordsRepository.deleteWord(word)
    }
}