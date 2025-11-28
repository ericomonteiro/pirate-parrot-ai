package com.github.ericomonteiro.copilot.ui.solution

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.github.ericomonteiro.copilot.db.Solution
import com.github.ericomonteiro.copilot.ui.components.CodeDisplay
import org.koin.compose.koinInject
import org.koin.core.parameter.parametersOf

@Composable
fun SolutionScreen(
    problemId: Long,
    onBackClick: () -> Unit
) {
    val viewModel: SolutionViewModel = koinInject { parametersOf(problemId) }
    val state by viewModel.state.collectAsState()
    
    Column(modifier = Modifier.fillMaxSize()) {
        // Header
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onBackClick) {
                Icon(Icons.Default.ArrowBack, "Back")
            }
            
            Text(
                text = state.problem?.problem_name ?: "",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.weight(1f).padding(horizontal = 8.dp)
            )
            
            // Language selector
            var expanded by remember { mutableStateOf(false) }
            Box {
                TextButton(onClick = { expanded = true }) {
                    Text(state.selectedLanguage)
                    Icon(Icons.Default.ArrowDropDown, null)
                }
                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    listOf("Kotlin", "Java", "Python", "JavaScript").forEach { lang ->
                        DropdownMenuItem(
                            text = { Text(lang) },
                            onClick = {
                                viewModel.selectLanguage(lang)
                                expanded = false
                            }
                        )
                    }
                }
            }
        }
        
        Divider()
        
        // Content
        when {
            state.isLoading -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        CircularProgressIndicator()
                        Spacer(modifier = Modifier.height(16.dp))
                        Text("Generating solution...")
                    }
                }
            }
            state.error != null -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.padding(16.dp)
                    ) {
                        Text(
                            "Error: ${state.error}",
                            color = MaterialTheme.colorScheme.error,
                            modifier = Modifier.padding(horizontal = 16.dp)
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Button(onClick = { viewModel.retry() }) {
                                Text("Retry")
                            }
                            OutlinedButton(
                                onClick = {
                                    copyToClipboard(state.error ?: "")
                                }
                            ) {
                                Text("Copy Error")
                            }
                        }
                    }
                }
            }
            state.solution != null -> {
                SolutionContent(solution = state.solution!!, language = state.selectedLanguage)
            }
            else -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            }
        }
    }
}

@Composable
fun SolutionContent(solution: Solution, language: String = "Kotlin") {
    var selectedTab by remember { mutableStateOf(0) }
    
    Column(modifier = Modifier.fillMaxSize()) {
        TabRow(selectedTabIndex = selectedTab) {
            Tab(
                selected = selectedTab == 0,
                onClick = { selectedTab = 0 },
                text = { Text("Code") }
            )
            Tab(
                selected = selectedTab == 1,
                onClick = { selectedTab = 1 },
                text = { Text("Explanation") }
            )
        }
        
        when (selectedTab) {
            0 -> CodeTab(code = solution.code, language = language)
            1 -> ExplanationTab(
                explanation = solution.explanation,
                timeComplexity = solution.time_complexity,
                spaceComplexity = solution.space_complexity
            )
        }
    }
}

@Composable
fun CodeTab(code: String, language: String = "Kotlin") {
    CodeDisplay(
        code = code,
        language = language,
        showLineNumbers = true
    )
}

@Composable
fun ExplanationTab(
    explanation: String,
    timeComplexity: String,
    spaceComplexity: String
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        Text(
            "Explanation",
            style = MaterialTheme.typography.titleMedium
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(explanation)
        
        Spacer(modifier = Modifier.height(16.dp))
        
        Text(
            "Complexity Analysis",
            style = MaterialTheme.typography.titleMedium
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text("Time: $timeComplexity")
        Text("Space: $spaceComplexity")
    }
}

private fun copyToClipboard(text: String) {
    try {
        val clipboard = java.awt.Toolkit.getDefaultToolkit().systemClipboard
        val stringSelection = java.awt.datatransfer.StringSelection(text)
        clipboard.setContents(stringSelection, null)
    } catch (e: Exception) {
        // Silently fail if clipboard access is not available
        e.printStackTrace()
    }
}
