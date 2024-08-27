package com.devluque.dictionaryai.ui.search

import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.OutlinedTextFieldDefaults.Container
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.devluque.dictionaryai.R
import com.devluque.dictionaryai.ui.theme.getColorScheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    onSearch: (String) -> Unit
) {
    val searchState = rememberSearchState()

    Column(
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 16.dp)
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
                ),
            textStyle = TextStyle(
                color = getColorScheme().onSurface // Texto visible en ambos temas
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
                        Icon(painter = painterResource(id = R.drawable.ic_search), contentDescription = "")
                    },
                    container = {},
                    placeholder = {
                        Text(text = "Significados de...")
                    }
                )
            }
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = {
                onSearch(searchState.searchText.value)
            },
            colors = ButtonDefaults.buttonColors().copy(
                containerColor = getColorScheme().primary,
                contentColor = getColorScheme().onPrimary
            ),
            modifier = Modifier
                .align(Alignment.CenterHorizontally),
            enabled = searchState.searchText.value.isNotEmpty()
        ) {
            Text(text = "Buscar")
        }
        //TODO: Add recent searches
//        Spacer(modifier = Modifier.height(24.dp))
//        Text(
//            text = "Búsquedas recientes",
//            style = TextStyle(
//                fontSize = 18.sp,
//                fontWeight = FontWeight(500)
//            ),
//            modifier = Modifier.weight(1f),
//            textAlign = TextAlign.Justify
//        )
    }
}