package com.devluque.dictionaryai.usecases

import com.devluque.dictionaryai.data.WordsRepository
import com.devluque.dictionaryai.domain.Word

class InsertWordUseCase(
    private val wordsRepository: WordsRepository
) {
    suspend operator fun invoke(word: String) = wordsRepository.insertWord(word)
}