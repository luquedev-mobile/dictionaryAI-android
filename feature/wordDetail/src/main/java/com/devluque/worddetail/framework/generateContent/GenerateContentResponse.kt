package com.devluque.worddetail.framework.generateContent

import com.devluque.domain.Content
import kotlinx.serialization.Serializable

@Serializable
data class GenerateContentResponse(
    val candidates: List<Candidate>
)

@Serializable
data class Candidate(
    val content: Content
)
