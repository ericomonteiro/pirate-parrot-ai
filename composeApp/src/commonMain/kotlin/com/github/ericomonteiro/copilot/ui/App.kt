package com.github.ericomonteiro.copilot.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import com.github.ericomonteiro.copilot.screenshot.captureScreenshot
import com.github.ericomonteiro.copilot.ui.search.SearchScreen
import com.github.ericomonteiro.copilot.ui.solution.SolutionScreen
import com.github.ericomonteiro.copilot.ui.settings.SettingsScreen
import com.github.ericomonteiro.copilot.ui.screenshot.ScreenshotAnalysisScreen
import kotlinx.coroutines.launch

enum class Screen {
    SEARCH, SOLUTION, SETTINGS, SCREENSHOT_ANALYSIS
}

@Composable
fun App() {
    var currentScreen by remember { mutableStateOf(Screen.SEARCH) }
    var selectedProblemId by remember { mutableStateOf<Long?>(null) }
    var screenshotBase64 by remember { mutableStateOf<String?>(null) }
    var opacity by remember { mutableStateOf(0.95f) }
    val scope = rememberCoroutineScope()
    
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
                    },
                    onScreenshotClick = {
                        scope.launch {
                            captureScreenshot().fold(
                                onSuccess = { base64 ->
                                    screenshotBase64 = base64
                                    currentScreen = Screen.SCREENSHOT_ANALYSIS
                                },
                                onFailure = { error ->
                                    println("Failed to capture screenshot: ${error.message}")
                                }
                            )
                        }
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
                Screen.SCREENSHOT_ANALYSIS -> {
                    screenshotBase64?.let { base64 ->
                        ScreenshotAnalysisScreen(
                            screenshotBase64 = base64,
                            onBackClick = {
                                currentScreen = Screen.SEARCH
                            }
                        )
                    }
                }
            }
        }
    }
}
