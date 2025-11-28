package com.github.ericomonteiro.copilot

import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.input.key.*
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowPosition
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import com.github.ericomonteiro.copilot.data.repository.ProblemRepository
import com.github.ericomonteiro.copilot.data.seedDatabase
import com.github.ericomonteiro.copilot.di.appModule
import com.github.ericomonteiro.copilot.ui.App
import org.koin.core.context.startKoin
import org.koin.java.KoinJavaComponent.get

fun main() = application {
    // Initialize Koin
    startKoin {
        modules(appModule)
    }
    
    Window(
        onCloseRequest = ::exitApplication,
        title = "Interview Assistant",
        state = rememberWindowState(
            width = 800.dp,
            height = 600.dp,
            position = WindowPosition(100.dp, 100.dp)
        ),
        undecorated = false,
        transparent = false,
        resizable = true,
        alwaysOnTop = true
    ) {
        // Seed database on first run
        LaunchedEffect(Unit) {
            val repository = get<ProblemRepository>(ProblemRepository::class.java)
            val problems = repository.getAllProblems()
            if (problems.isEmpty()) {
                seedDatabase(repository)
            }
        }
        
        App()
    }
}