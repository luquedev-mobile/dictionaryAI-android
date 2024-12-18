package com.devluque

import com.devluque.entities.Word

fun sampleWord(word: String) = Word(
    word = word
)

fun sampleWords(vararg words: String) = words.map { sampleWord(it) }