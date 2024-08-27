package com.devluque.dictionaryai.data

import com.devluque.dictionaryai.data.generateContent.GenerateContentResponse
import com.devluque.dictionaryai.data.wordDetail.WordDetailRequest

class AiRepository {
    suspend fun generateWordDetail(
        wordDetailRequest: WordDetailRequest
    ): GenerateContentResponse? {
        return try {
            AiClient.instance
                .generateWordDetail(
                    wordDetailRequest = wordDetailRequest
                )
        } catch (e: Exception) {
            null
        }
    }
}