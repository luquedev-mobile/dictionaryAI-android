package com.devluque.search.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.devluque.common.theme.getColorScheme
import com.devluque.entities.Word
import com.devluque.search.R
import com.devluque.search.ui.TEST_TAGS.LIST_WORDS_

object TEST_TAGS {
    const val SEARCH_BUTTON_TAG = "SEARCH_BUTTON"
    const val BASIC_TEXT_FIELD_TAG = "BASIC_TEXT_FIELD"
    const val BUTTON_DELETE_TAG = "BUTTON_DELETE"
    const val LIST_WORDS_ = "list_words"
}

@Composable
fun SearchScreen(
    onSearch: (String) -> Unit,
    vm: SearchViewModel
) {
    val words = vm.uiState.collectAsState()
    SearchScreen(
        onSearchMeaningOfWord = { wordToGetMeaning ->
            vm.insertWord(
                wordToGetMeaning
            )
            onSearch(wordToGetMeaning)
        },
        searchWords = {
            vm.searchWords(it)
        },
        onDelete = vm::deleteWord,
        words = words.value
    )
}

@Composable
fun SearchScreen(
    onSearchMeaningOfWord: (String) -> Unit,
    searchWords: (String) -> Unit,
    onDelete: (String) -> Unit,
    words: List<Word>,
) {
    val searchState = rememberSearchState()

    searchState.SearchTextChange {
        searchWords(it)
    }

    Column(
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 16.dp)
    ) {
        Search(searchState)
        Spacer(modifier = Modifier.height(16.dp))
        SearchButton(
            isEnabled = searchState.searchText.value.isNotEmpty(),
            modifier = Modifier
                .align(Alignment.CenterHorizontally),
            onClick = {
                onSearchMeaningOfWord(searchState.searchText.value)
            }
        )
        if (words.isNotEmpty()) {
            Spacer(modifier = Modifier.height(24.dp))
            Text(
                text = "Búsquedas recientes",
                style = TextStyle(
                    fontSize = 18.sp,
                    fontWeight = FontWeight(500)
                ),
                textAlign = TextAlign.Justify
            )
            Spacer(modifier = Modifier.height(24.dp))
            LazyColumn {
                items(words.size) { index ->
                    WordItem(
                        word = words[index],
                        onDelete = {
                            onDelete(it.word)
                        },
                        onSearch = {
                            onSearchMeaningOfWord(it)
                        },
                        mustShowDivider = index != words.size - 1
                    )
                }
            }
        }
    }
}

@Composable
fun SearchButton(
    isEnabled: Boolean,
    modifier: Modifier,
    onClick: () -> Unit
) {
    Button(
        onClick = { onClick() },
        colors = ButtonDefaults.buttonColors().copy(
            containerColor = getColorScheme().primary,
            contentColor = getColorScheme().onPrimary
        ),
        modifier = modifier
            .testTag(TEST_TAGS.SEARCH_BUTTON_TAG),
        enabled = isEnabled
    ) {
        Text(text = "Buscar")
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun Search(
    searchState: SearchState
) {
    BasicTextField(
        value = searchState.searchText.value,
        onValueChange = {
            searchState.searchText.value = it
        },
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = getColorScheme().surface,
                shape = RoundedCornerShape(8.dp)
            )
            .testTag(TEST_TAGS.BASIC_TEXT_FIELD_TAG),
        textStyle = TextStyle(
            color = com.devluque.common.theme.getColorScheme().onSurface // Texto visible en ambos temas
        ),
        cursorBrush = SolidColor(getColorScheme().primary),
        decorationBox = @Composable { innerTextField ->
            TextFieldDefaults.DecorationBox(
                value = searchState.searchText.value,
                innerTextField = innerTextField,
                enabled = true,
                singleLine = true,
                visualTransformation = VisualTransformation.None,
                interactionSource = searchState.interactionSource,
                leadingIcon = {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_search),
                        contentDescription = ""
                    )
                },
                container = {},
                placeholder = {
                    Text(text = "Significados de...")
                }
            )
        }
    )
}

@Composable
private fun WordItem(
    word: Word,
    onDelete: (Word) -> Unit,
    onSearch: (String) -> Unit,
    mustShowDivider: Boolean
) {
    Column {
        ListItem(
            headlineContent = {
                Item(
                    word = word.word,
                    onClick = { word ->
                        onSearch(word)
                    }
                )
            },
            trailingContent = {
                Icon(
                    painter = painterResource(id = R.drawable.ic_x),
                    contentDescription = "",
                    modifier = Modifier
                        .size(16.dp)
                        .clickable {
                            onDelete(word)
                        }
                        .testTag("${TEST_TAGS.BUTTON_DELETE_TAG}${word.word}")
                )
            }
        )
        if (mustShowDivider) {
            HorizontalDivider()
        }
    }
}

@Composable
private fun Item(
    word: String,
    onClick: (String) -> Unit
) {
    Column(
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick(word) }
            .testTag("${LIST_WORDS_}$word")
    ) {
        Text(
            text = word
        )
    }
}
