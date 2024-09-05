package com.devluque.dictionaryai.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class WordDetailItem(
    val meanings: List<Meaning>? = null,
    val word: String
)

@Serializable
data class Meaning(
    @SerialName("example_english")
    val exampleEnglish: String? = null,
    @SerialName("example_spanish")
    val exampleSpanish: String? = null,
    val explanation: String? = null,
    val mean: String,
    val partOfSpeech: String? = null
)