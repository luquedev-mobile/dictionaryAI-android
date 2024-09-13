package com.devluque.dictionaryai.ui.wordDetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devluque.dictionaryai.Result
import com.devluque.dictionaryai.framework.remote.wordDetail.WordDetailRequest
import com.devluque.dictionaryai.domain.WordDetailItem
import com.devluque.dictionaryai.usecases.FetchGenerateWordDetailUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update

enum class SpeakerModer(val speechRate: Float) {
    Slow(0.2f),
    Normal(1f)
}

class WordDetailViewModel(
    private val fetchGenerateWordDetailUseCase: FetchGenerateWordDetailUseCase
) : ViewModel() {
    private val controlUi = MutableStateFlow(ControlUi())

    sealed class WordDetailEvent {
        data class Speak(
            val text: String,
            val speakerModer: SpeakerModer
        ) : WordDetailEvent()
    }

    private var _events = Channel<WordDetailEvent>()
    val events = _events.receiveAsFlow()

    data class ControlUi(
        val isGettingData: Boolean = false,
        val historicalRequest: WordDetailRequest? = null
    )

    @OptIn(ExperimentalCoroutinesApi::class)
    val uiState: StateFlow<Result<WordDetailItem>> =
        controlUi
            .filter { it.historicalRequest != null && it.isGettingData }
            .flatMapLatest { request ->
                flow {
                    emit(Result.Loading)
                    request.historicalRequest?.let { request ->
                        emitAll(
                            fetchGenerateWordDetailUseCase(request).also {
                                controlUi.update {
                                    it.copy(
                                        isGettingData = false
                                    )
                                }
                            }
                        )
                    } ?: run {
                        emit(Result.Error(Exception("Request is null")))
                    }
                }
            }.stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = Result.Loading
            )

    fun init(wordDetailRequest: WordDetailRequest) {
        controlUi.update {
            it.copy(
                historicalRequest = wordDetailRequest,
                isGettingData = true
            )
        }
    }

    fun speak(text: String, speakerModer: SpeakerModer = SpeakerModer.Normal) {
        _events.trySend(WordDetailEvent.Speak(text, speakerModer))
    }

    fun refresh() {
        controlUi.update {
            it.copy(isGettingData = true)
        }
    }
}
