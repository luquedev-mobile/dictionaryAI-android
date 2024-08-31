package com.devluque.dictionaryai.data.wordDetail

import kotlinx.serialization.Serializable

@Serializable
data class WordDetailResponseItem(
    val meanings: List<Meaning>? = null,
    val word: String
)

@Serializable
data class Meaning(
    val example_english: String? = null,
    val example_spanish: String? = null,
    val explanation: String? = null,
    val mean: String,
    val partOfSpeech: String? = null
)