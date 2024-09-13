package com.devluque.dictionaryai.domain

data class WordDetailItem(
    val meanings: List<Meaning>? = null,
    val word: String
)

data class Meaning(
    val exampleEnglish: String? = null,
    val exampleSpanish: String? = null,
    val explanation: String? = null,
    val mean: String,
    val partOfSpeech: String? = null
)