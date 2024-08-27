package com.devluque.dictionaryai.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.devluque.dictionaryai.ui.common.topBar.TopAppBarOvalShape
import com.devluque.dictionaryai.ui.common.topBar.TopBar
import com.devluque.dictionaryai.ui.search.SearchScreen
import com.devluque.dictionaryai.ui.theme.DictionaryAITheme
import com.devluque.dictionaryai.ui.theme.getColorScheme
import com.devluque.dictionaryai.ui.wordDetail.WordDetail
import com.devluque.dictionaryai.ui.wordDetail.WordDetailViewModel
import com.google.accompanist.systemuicontroller.rememberSystemUiController

sealed class NavScreen(val route: String) {
    data object Search : NavScreen("search")
    data object WordDetail : NavScreen("wordDetail/{${NavArgs.Word.key}}") {
        fun createRoute(word: String) = "wordDetail/$word"
    }
}

enum class NavArgs(val key: String) {
    Word("word")
}

@Composable
fun AppScaffold() {
    val navController = rememberNavController()
    val systemUiController = rememberSystemUiController()
    systemUiController.setStatusBarColor(getColorScheme().primary)
    var showBottomBar by remember { mutableStateOf(true) }
    var topAppBar by remember { mutableStateOf<@Composable () -> Unit>({}) }

    DictionaryAITheme(
        dynamicColor = false
    ) {
        Surface(
            modifier = Modifier.fillMaxSize()
        ) {
            Scaffold(
                topBar = {
                    topAppBar()
                },
                contentWindowInsets = WindowInsets.safeDrawing,
            ) { paddingValues ->
                Column(
                    modifier = Modifier
                        .padding(paddingValues)
                ) {
                    NavHost(
                        navController = navController,
                        startDestination = NavScreen.Search.route
                    ) {
                        composable(NavScreen.Search.route) {
                            showBottomBar = true
                            topAppBar = { TopAppBarOvalShape() }
                            SearchScreen(
                                onSearch = {
                                    navController.navigate(NavScreen.WordDetail.createRoute(it))
                                }
                            )
                        }

                        composable(
                            route = NavScreen.WordDetail.route,
                            arguments = listOf(navArgument(NavArgs.Word.key) {
                                type = NavType.StringType
                            })
                        ) { backStackEntry ->
                            showBottomBar = false
                            val word =
                                requireNotNull(backStackEntry.arguments?.getString(NavArgs.Word.key))
                            topAppBar = {
                                TopBar(title = "Detalle de la palabra") {
                                    navController.popBackStack()
                                }
                            }
                            WordDetail(
                                word = word,
                                viewModel = viewModel {
                                    WordDetailViewModel()
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}