package com.devluque.domain.remote.worddetail

import com.devluque.domain.remote.common.Content
import com.devluque.domain.remote.common.ItemTypeString
import com.devluque.domain.remote.common.SafetySettings
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RemoteWordDetailRequest(
    var contents: MutableList<Content>,
    val systemInstruction: Content,
    val generationConfig: WordDetailGenerationConfig,
    val safetySettings: List<SafetySettings>
)

@Serializable
data class WordDetailGenerationConfig(
    @SerialName("response_mime_type")
    val responseMimeType: String,
    @SerialName("response_schema")
    val responseSchema: WordDetailResponseSchema,
    val temperature: Double
)

@Serializable
data class WordDetailResponseSchema(
    val properties: WordDetailProperties,
    val type: String,
    val required: List<String>,
    val nullable: String
)

@Serializable
data class WordDetailProperties(
    val meanings: Meanings,
    val word: ItemTypeString
)

@Serializable
data class Meanings(
    val items: WordDetailItemsProperties,
    val type: String,
    val nullable: String
)

@Serializable
data class WordDetailItemsProperties(
    val properties: WordDetailMeaningProperties,
    val type: String,
    val required: List<String>
)

@Serializable
data class WordDetailMeaningProperties(
    @SerialName("example_english")
    val exampleEnglish: ItemTypeString,
    @SerialName("example_spanish")
    val exampleSpanish: ItemTypeString,
    val explanation: ItemTypeString,
    val mean: ItemTypeString,
    val partOfSpeech: ItemTypeString
)