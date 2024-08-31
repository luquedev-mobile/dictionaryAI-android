package com.devluque.dictionaryai.data.common

import kotlinx.serialization.Serializable

@Serializable
data class Content(
    val parts: List<Part>,
    val role: String? = null
)

@Serializable
data class Part(
    val text: String
)

@Serializable
data class ItemTypeString(
    val type: String,
    val description: String? = null
)

@Serializable
data class SafetySettings(
    val category: String,
    val threshold: String
)