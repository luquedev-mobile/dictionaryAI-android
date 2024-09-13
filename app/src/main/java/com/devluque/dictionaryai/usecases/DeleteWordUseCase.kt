package com.devluque.dictionaryai.usecases

import com.devluque.dictionaryai.data.WordsRepository

class DeleteWordUseCase(
    private val wordsRepository: WordsRepository
) {
    suspend operator fun invoke(word: String) {
        wordsRepository.deleteWord(word)
    }
}