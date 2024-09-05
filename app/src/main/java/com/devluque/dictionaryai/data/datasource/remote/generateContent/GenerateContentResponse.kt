package com.devluque.dictionaryai.data.datasource.remote.generateContent

import com.devluque.dictionaryai.data.datasource.remote.common.Content
import kotlinx.serialization.Serializable

@Serializable
data class GenerateContentResponse(
    val candidates: List<Candidate>
)

@Serializable
data class Candidate(
    val content: Content
)
