package com.devluque.dictionaryai.data.generateContent
import com.devluque.dictionaryai.data.common.Content
import kotlinx.serialization.Serializable

@Serializable
data class GenerateContentResponse(
    val candidates: List<Candidate>
)

@Serializable
data class Candidate(
    val content: Content
)
