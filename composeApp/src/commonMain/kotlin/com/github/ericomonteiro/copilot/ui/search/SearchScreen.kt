package com.github.ericomonteiro.copilot.ui.search

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.github.ericomonteiro.copilot.db.Problem
import org.koin.compose.koinInject

@Composable
fun SearchScreen(
    onProblemSelected: (Long) -> Unit,
    onSettingsClick: () -> Unit,
    onScreenshotClick: () -> Unit = {},
    viewModel: SearchViewModel = koinInject()
) {
    val state by viewModel.state.collectAsState()
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Header
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                "Interview Assistant",
                style = MaterialTheme.typography.headlineSmall
            )
            Row {
                IconButton(onClick = onScreenshotClick) {
                    Icon(Icons.Default.CameraAlt, "Capture Screenshot")
                }
                IconButton(onClick = onSettingsClick) {
                    Icon(Icons.Default.Settings, "Settings")
                }
            }
        }
        
        Spacer(modifier = Modifier.height(16.dp))
        
        // Search Bar
        OutlinedTextField(
            value = state.query,
            onValueChange = { viewModel.onQueryChanged(it) },
            modifier = Modifier.fillMaxWidth(),
            placeholder = { Text("Search problems...") },
            leadingIcon = { Icon(Icons.Default.Search, null) },
            singleLine = true
        )
        
        Spacer(modifier = Modifier.height(16.dp))
        
        // Results
        if (state.isLoading) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        } else if (state.results.isEmpty()) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("No problems found. Add some problems to get started.")
            }
        } else {
            LazyColumn(modifier = Modifier.fillMaxSize()) {
                items(state.results) { problem ->
                    ProblemCard(
                        problem = problem,
                        onClick = { onProblemSelected(problem.id) }
                    )
                }
            }
        }
    }
}

@Composable
fun ProblemCard(problem: Problem, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .clickable(onClick = onClick)
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = problem.problem_name,
                    style = MaterialTheme.typography.titleMedium
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "${problem.platform} • ${problem.difficulty}",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            
            Spacer(modifier = Modifier.width(8.dp))
            
            // Difficulty badge
            Surface(
                color = when (problem.difficulty) {
                    "Easy" -> Color(0xFF4CAF50)
                    "Medium" -> Color(0xFFFF9800)
                    "Hard" -> Color(0xFFF44336)
                    else -> Color.Gray
                },
                shape = RoundedCornerShape(4.dp)
            ) {
                Text(
                    text = problem.difficulty,
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                    style = MaterialTheme.typography.labelSmall,
                    color = Color.White
                )
            }
        }
    }
}
