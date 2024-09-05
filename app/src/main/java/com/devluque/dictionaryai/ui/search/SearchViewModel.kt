package com.devluque.dictionaryai.ui.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devluque.dictionaryai.data.model.Word
import com.devluque.dictionaryai.data.WordsRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SearchViewModel(
    private val wordsRepository: WordsRepository
): ViewModel() {
    private val recentWords: Flow<List<Word>> = wordsRepository.getRecentWords
    private val searchWords: MutableStateFlow<String> = MutableStateFlow("")

    @OptIn(ExperimentalCoroutinesApi::class)
    val uiState: StateFlow<List<Word>> =
        searchWords
            .flatMapLatest { query ->
                if (query.isEmpty()) {
                    recentWords
                } else {
                    wordsRepository.searchWords(query)
                }
            }
        .stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    fun searchWords(query: String) {
        searchWords.update {
            query
        }
    }

    fun insertWord(word: Word) {
        viewModelScope.launch {
            wordsRepository.insertWord(word)
        }
    }
    fun deleteWord(word: String) {
        viewModelScope.launch {
            wordsRepository.deleteWord(word)
        }
    }
}