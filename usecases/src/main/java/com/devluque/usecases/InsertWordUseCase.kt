package com.devluque.usecases

import com.devluque.data.WordsRepository

class InsertWordUseCase(
    private val wordsRepository: WordsRepository
) {
    suspend operator fun invoke(word: String) = wordsRepository.insertWord(word)
}