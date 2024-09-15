package com.devluque.worddetail.framework

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RemoteWordDetailItem(
    val meanings: List<RemoteMeaning>? = null,
    val word: String
)

@Serializable
data class RemoteMeaning(
    @SerialName("example_english")
    val exampleEnglish: String? = null,
    @SerialName("example_spanish")
    val exampleSpanish: String? = null,
    val explanation: String? = null,
    val mean: String,
    val partOfSpeech: String? = null
)