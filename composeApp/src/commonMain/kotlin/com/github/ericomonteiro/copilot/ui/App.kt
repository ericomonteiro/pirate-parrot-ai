package com.github.ericomonteiro.copilot.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.github.ericomonteiro.copilot.ui.certification.CertificationAnalysisScreen
import com.github.ericomonteiro.copilot.ui.history.ScreenshotHistoryScreen
import com.github.ericomonteiro.copilot.ui.settings.SettingsScreen
import com.github.ericomonteiro.copilot.ui.screenshot.ScreenshotAnalysisScreen

enum class Screen {
    SCREENSHOT_ANALYSIS, CERTIFICATION_ANALYSIS, SETTINGS, HISTORY
}

@Composable
fun App(
    onHideFromCaptureChanged: (Boolean) -> Unit = {},
    screenshotTrigger: Int = 0
) {
    var currentScreen by remember { mutableStateOf(Screen.SCREENSHOT_ANALYSIS) }
    var autoCapture by remember { mutableStateOf(false) }
    
    // Handle screenshot request from keyboard shortcut - respects current screen context
    LaunchedEffect(screenshotTrigger) {
        if (screenshotTrigger > 0) {
            autoCapture = true
            // Don't change screen - trigger capture in current context (code or certification)
            // If on settings or history, go to the last active screen (default to screenshot analysis)
            if (currentScreen == Screen.SETTINGS || currentScreen == Screen.HISTORY) {
                currentScreen = Screen.SCREENSHOT_ANALYSIS
            }
        }
    }
    
    MaterialTheme(
        colorScheme = darkColorScheme()
    ) {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            when (currentScreen) {
                Screen.SCREENSHOT_ANALYSIS -> {
                    ScreenshotAnalysisScreen(
                        autoCapture = autoCapture,
                        onSettingsClick = {
                            currentScreen = Screen.SETTINGS
                        },
                        onCertificationClick = {
                            currentScreen = Screen.CERTIFICATION_ANALYSIS
                        },
                        onAutoCaptureConsumed = {
                            autoCapture = false
                        }
                    )
                }
                Screen.CERTIFICATION_ANALYSIS -> {
                    CertificationAnalysisScreen(
                        autoCapture = autoCapture,
                        onSettingsClick = {
                            currentScreen = Screen.SETTINGS
                        },
                        onCodeChallengeClick = {
                            currentScreen = Screen.SCREENSHOT_ANALYSIS
                        },
                        onAutoCaptureConsumed = {
                            autoCapture = false
                        }
                    )
                }
                Screen.SETTINGS -> SettingsScreen(
                    onCloseClick = {
                        currentScreen = Screen.SCREENSHOT_ANALYSIS
                    },
                    onHideFromCaptureChanged = { hide ->
                        onHideFromCaptureChanged(hide)
                    },
                    onHistoryClick = {
                        currentScreen = Screen.HISTORY
                    }
                )
                Screen.HISTORY -> ScreenshotHistoryScreen(
                    onBackClick = {
                        currentScreen = Screen.SETTINGS
                    }
                )
            }
        }
    }
}
