package com.devluque.dictionaryai.ui.wordDetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devluque.dictionaryai.data.AiRepository
import com.devluque.dictionaryai.data.common.Content
import com.devluque.dictionaryai.data.common.Part
import com.devluque.dictionaryai.data.wordDetail.WordDetailRequest
import com.devluque.dictionaryai.data.wordDetail.WordDetailResponseItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json

class WordDetailViewModel : ViewModel() {
    var state = MutableStateFlow(UiState())
        private set
    private lateinit var historicalRequest: WordDetailRequest

    private val repository = AiRepository()

    data class UiState(
        val wordDetail: WordDetailResponseItem? = null,
        val loading: Boolean = false,
        val error: Boolean = false
    )

    fun init(wordDetailRequest: WordDetailRequest) {
        historicalRequest = wordDetailRequest
        getContent()
    }

    fun refresh() {
        getContent()
    }

    private fun getContent() {
        viewModelScope.launch {
            state.update {
                it.copy(
                    loading = true,
                    error = false
                )
            }
            try {
                val response = repository.generateWordDetail(
                    wordDetailRequest = historicalRequest
                )

                response?.let {
                    val responseJson =
                        Json.decodeFromString<List<WordDetailResponseItem>>(
                            response.candidates[0].content.parts[0].text
                        )

                    state.update {
                        it.copy(
                            loading = false,
                            wordDetail = responseJson[0]
                        )
                    }
                } ?: run {
                    state.update {
                        it.copy(
                            loading = false,
                            error = true
                        )
                    }
                }
            } catch (e: Exception) {
                state.update {
                    it.copy(
                        loading = false,
                        error = true
                    )
                }
            }
        }
    }
}
