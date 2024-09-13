package com.devluque.dictionaryai.ui.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devluque.dictionaryai.domain.Word
import com.devluque.dictionaryai.usecases.DeleteWordUseCase
import com.devluque.dictionaryai.usecases.GetRecentWordsUseCase
import com.devluque.dictionaryai.usecases.InsertWordUseCase
import com.devluque.dictionaryai.usecases.SearchWordsUseCase
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
    getRecentWordsUseCase: GetRecentWordsUseCase,
    private val searchWordsUseCase: SearchWordsUseCase,
    private val insertWordUseCase: InsertWordUseCase,
    private val deleteWordUseCase: DeleteWordUseCase,
): ViewModel() {
    private val recentWords: Flow<List<Word>> = getRecentWordsUseCase()
    private val searchWords: MutableStateFlow<String> = MutableStateFlow("")

    @OptIn(ExperimentalCoroutinesApi::class)
    val uiState: StateFlow<List<Word>> =
        searchWords
            .flatMapLatest { query ->
                if (query.isEmpty()) {
                    recentWords
                } else {
                    searchWordsUseCase(query)
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

    fun insertWord(word: String) {
        viewModelScope.launch {
            insertWordUseCase(word)
        }
    }
    fun deleteWord(word: String) {
        viewModelScope.launch {
            deleteWordUseCase(word)
        }
    }
}