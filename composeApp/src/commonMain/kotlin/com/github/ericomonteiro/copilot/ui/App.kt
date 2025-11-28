package com.github.ericomonteiro.copilot.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import com.github.ericomonteiro.copilot.ui.search.SearchScreen
import com.github.ericomonteiro.copilot.ui.solution.SolutionScreen
import com.github.ericomonteiro.copilot.ui.settings.SettingsScreen

enum class Screen {
    SEARCH, SOLUTION, SETTINGS
}

@Composable
fun App() {
    var currentScreen by remember { mutableStateOf(Screen.SEARCH) }
    var selectedProblemId by remember { mutableStateOf<Long?>(null) }
    var opacity by remember { mutableStateOf(0.95f) }
    
    MaterialTheme(
        colorScheme = darkColorScheme()
    ) {
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .alpha(opacity),
            color = MaterialTheme.colorScheme.background
        ) {
            when (currentScreen) {
                Screen.SEARCH -> SearchScreen(
                    onProblemSelected = { problemId ->
                        selectedProblemId = problemId
                        currentScreen = Screen.SOLUTION
                    },
                    onSettingsClick = {
                        currentScreen = Screen.SETTINGS
                    }
                )
                Screen.SOLUTION -> SolutionScreen(
                    problemId = selectedProblemId ?: 0,
                    onBackClick = {
                        currentScreen = Screen.SEARCH
                    }
                )
                Screen.SETTINGS -> SettingsScreen(
                    onCloseClick = {
                        currentScreen = Screen.SEARCH
                    },
                    onOpacityChanged = { newOpacity ->
                        opacity = newOpacity
                    }
                )
            }
        }
    }
}
