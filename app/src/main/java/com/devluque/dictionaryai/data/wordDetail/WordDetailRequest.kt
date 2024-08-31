package com.devluque.dictionaryai.data.wordDetail

import com.devluque.dictionaryai.data.common.Content
import com.devluque.dictionaryai.data.common.ItemTypeString
import com.devluque.dictionaryai.data.common.SafetySettings
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class WordDetailRequest(
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
    val items: WordDetailItemsSchema,
    val type: String,
    val nullable: String
)

@Serializable
data class WordDetailItemsSchema(
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