package com.devluque.worddetail.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devluque.domain.Result
import com.devluque.domain.SpeakerModer
import com.devluque.entities.RemoteWordDetailRequest
import com.devluque.entities.WordDetailItem
import com.devluque.usecases.FetchGenerateWordDetailUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
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
import javax.inject.Inject

@HiltViewModel
class WordDetailViewModel @Inject constructor(
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
        val historicalRequest: RemoteWordDetailRequest? = null
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
                initialValue = Result.Idle
            )

    fun init(remoteWordDetailRequest: RemoteWordDetailRequest) {
        controlUi.update {
            it.copy(
                historicalRequest = remoteWordDetailRequest,
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
