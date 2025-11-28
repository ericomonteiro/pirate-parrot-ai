# Developer Implementation Guide

## Overview

This guide will help you complete the MVP implementation. The foundation is ready - you just need to add the UI and wire everything together.

---

## What's Already Done ✅

1. **Dependencies**: All libraries configured in `gradle/libs.versions.toml`
2. **Database**: Schema and repository fully implemented
3. **AI Service**: OpenAI integration ready to use
4. **Documentation**: Complete user and developer guides

---

## Implementation Roadmap

### Phase 1: UI Implementation (Priority: HIGH)

#### Step 1.1: Create App.kt (Main Application)

**File**: `composeApp/src/commonMain/kotlin/com/github/ericomonteiro/copilot/ui/App.kt`

```kotlin
package com.github.ericomonteiro.copilot.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import com.github.ericomonteiro.copilot.ui.search.SearchScreen
import com.github.ericomonteiro.copilot.ui.solution.SolutionScreen
import com.github.ericomonteiro.copilot.ui.settings.SettingsScreen
import org.koin.compose.koinInject

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
```

#### Step 1.2: Implement SearchScreen

**File**: `composeApp/src/commonMain/kotlin/com/github/ericomonteiro/copilot/ui/search/SearchScreen.kt`

See `specs/mvp-implementation-plan.md` section 2.3 for complete code.

Key components:
- Search bar with real-time filtering
- Problem list with cards
- Difficulty badges
- Settings button

#### Step 1.3: Implement SolutionScreen

**File**: `composeApp/src/commonMain/kotlin/com/github/ericomonteiro/copilot/ui/solution/SolutionScreen.kt`

See `specs/mvp-implementation-plan.md` section 2.3 for complete code.

Key components:
- Language selector dropdown
- Tab layout (Code / Explanation)
- Code viewer with monospace font
- Loading and error states

#### Step 1.4: Implement SettingsScreen

**File**: `composeApp/src/commonMain/kotlin/com/github/ericomonteiro/copilot/ui/settings/SettingsScreen.kt`

See `specs/mvp-implementation-plan.md` section 2.3 for complete code.

Key components:
- API key input (password field)
- Opacity slider
- Stealth mode toggles
- Save button

#### Step 1.5: Create ViewModels

**SearchViewModel.kt**:
```kotlin
package com.github.ericomonteiro.copilot.ui.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.ericomonteiro.copilot.data.repository.ProblemRepository
import com.github.ericomonteiro.copilot.db.Problem
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class SearchViewModel(
    private val repository: ProblemRepository
) : ViewModel() {
    
    private val _state = MutableStateFlow(SearchState())
    val state: StateFlow<SearchState> = _state.asStateFlow()
    
    init {
        loadProblems()
    }
    
    fun onQueryChanged(query: String) {
        _state.update { it.copy(query = query) }
        searchProblems(query)
    }
    
    private fun searchProblems(query: String) {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            val results = if (query.isBlank()) {
                repository.getAllProblems()
            } else {
                repository.searchProblems(query)
            }
            _state.update { it.copy(results = results, isLoading = false) }
        }
    }
    
    private fun loadProblems() {
        searchProblems("")
    }
}

data class SearchState(
    val query: String = "",
    val results: List<Problem> = emptyList(),
    val isLoading: Boolean = false
)
```

**SolutionViewModel.kt** and **SettingsViewModel.kt**: See MVP plan for complete code.

---

### Phase 2: Dependency Injection (Priority: HIGH)

#### Step 2.1: Create Koin Module

**File**: `composeApp/src/commonMain/kotlin/com/github/ericomonteiro/copilot/di/AppModule.kt`

```kotlin
package com.github.ericomonteiro.copilot.di

import com.github.ericomonteiro.copilot.ai.AIService
import com.github.ericomonteiro.copilot.ai.HttpClientFactory
import com.github.ericomonteiro.copilot.ai.OpenAIService
import com.github.ericomonteiro.copilot.data.repository.ProblemRepository
import com.github.ericomonteiro.copilot.db.Database
import com.github.ericomonteiro.copilot.ui.search.SearchViewModel
import com.github.ericomonteiro.copilot.ui.solution.SolutionViewModel
import com.github.ericomonteiro.copilot.ui.settings.SettingsViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    // Database
    single { createDatabase() }
    
    // Repository
    single { ProblemRepository(get()) }
    
    // HTTP Client
    single { HttpClientFactory.create() }
    
    // AI Service
    single<AIService> {
        val apiKey = getApiKey(get())
        OpenAIService(apiKey, get())
    }
    
    // ViewModels
    viewModel { SearchViewModel(get()) }
    viewModel { (problemId: Long) -> 
        SolutionViewModel(problemId, get(), get())
    }
    viewModel { SettingsViewModel(get()) }
}

// Platform-specific database creation
expect fun createDatabase(): Database

// Get API key from settings or environment
suspend fun getApiKey(repository: ProblemRepository): String {
    return repository.getSetting("api_key") 
        ?: System.getenv("OPENAI_API_KEY") 
        ?: ""
}
```

#### Step 2.2: Implement Platform-Specific Database Creation

**File**: `composeApp/src/jvmMain/kotlin/com/github/ericomonteiro/copilot/di/DatabaseFactory.kt`

```kotlin
package com.github.ericomonteiro.copilot.di

import app.cash.sqldelight.driver.jdbc.sqlite.JdbcSqliteDriver
import com.github.ericomonteiro.copilot.db.Database
import java.io.File

actual fun createDatabase(): Database {
    val databasePath = getDatabasePath()
    val driver = JdbcSqliteDriver("jdbc:sqlite:$databasePath")
    
    // Create schema if needed
    if (!File(databasePath).exists()) {
        Database.Schema.create(driver)
    }
    
    return Database(driver)
}

private fun getDatabasePath(): String {
    val userHome = System.getProperty("user.home")
    val appDir = File(userHome, ".interviewassistant")
    if (!appDir.exists()) {
        appDir.mkdirs()
    }
    return File(appDir, "database.db").absolutePath
}
```

---

### Phase 3: Main Entry Point (Priority: HIGH)

#### Step 3.1: Create Main.kt

**File**: `composeApp/src/jvmMain/kotlin/com/github/ericomonteiro/copilot/main.kt`

```kotlin
package com.github.ericomonteiro.copilot

import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowPosition
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import com.github.ericomonteiro.copilot.di.appModule
import com.github.ericomonteiro.copilot.ui.App
import org.koin.core.context.startKoin

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
        App()
    }
}
```

---

### Phase 4: Data Seeding (Priority: MEDIUM)

#### Step 4.1: Create Seed Data Script

**File**: `composeApp/src/commonMain/kotlin/com/github/ericomonteiro/copilot/data/SeedData.kt`

```kotlin
package com.github.ericomonteiro.copilot.data

import com.github.ericomonteiro.copilot.data.repository.ProblemRepository
import kotlinx.datetime.Clock

suspend fun seedDatabase(repository: ProblemRepository) {
    val problems = listOf(
        Triple("LeetCode", "Two Sum", "Given an array of integers nums and an integer target, return indices of the two numbers such that they add up to target."),
        Triple("LeetCode", "Reverse Linked List", "Given the head of a singly linked list, reverse the list, and return the reversed list."),
        Triple("LeetCode", "Valid Parentheses", "Given a string s containing just the characters '(', ')', '{', '}', '[' and ']', determine if the input string is valid."),
        Triple("LeetCode", "Merge Two Sorted Lists", "You are given the heads of two sorted linked lists list1 and list2. Merge the two lists into one sorted list."),
        Triple("LeetCode", "Best Time to Buy and Sell Stock", "You are given an array prices where prices[i] is the price of a given stock on the ith day."),
        // Add 45 more problems here...
    )
    
    problems.forEach { (platform, name, description) ->
        repository.insertProblem(
            platform = platform,
            problemName = name,
            problemNumber = null,
            difficulty = "Medium",
            description = description,
            createdAt = Clock.System.now().toEpochMilliseconds()
        )
    }
}
```

Call this in main.kt on first run:
```kotlin
LaunchedEffect(Unit) {
    val repository = get<ProblemRepository>()
    val problems = repository.getAllProblems()
    if (problems.isEmpty()) {
        seedDatabase(repository)
    }
}
```

---

### Phase 5: Platform Features (Priority: MEDIUM)

#### Step 5.1: Window Management (Simplified for MVP)

For MVP, we'll use basic Compose Desktop features. Advanced stealth features can be added later.

**In main.kt**, add:
```kotlin
Window(
    // ... other properties
    alwaysOnTop = true,  // Always on top
    undecorated = false, // Keep window decorations for MVP
    transparent = false  // No transparency for MVP
) {
    // Set opacity in App.kt instead
    App()
}
```

#### Step 5.2: Hotkey System (Simplified for MVP)

For MVP, skip global hotkeys. Users can use Alt+Tab or Cmd+Tab to switch windows.

**Future enhancement**: Implement global hotkeys using JNA (see full implementation plan).

---

## Testing Checklist

### Manual Testing

- [ ] App launches without errors
- [ ] Search bar filters problems
- [ ] Clicking a problem opens solution screen
- [ ] Language selector changes language
- [ ] AI generates solution (requires API key)
- [ ] Solution is cached (second load is instant)
- [ ] Settings screen saves API key
- [ ] Opacity slider works
- [ ] Back button returns to search
- [ ] App can be closed properly

### Unit Testing

Create basic tests:

```kotlin
// ProblemRepositoryTest.kt
class ProblemRepositoryTest {
    @Test
    fun `search returns results`() = runTest {
        // Test implementation
    }
}
```

---

## Build and Run

### Development

```bash
# Set API key
export OPENAI_API_KEY="sk-your-key"

# Run
./gradlew :composeApp:run
```

### Production Build

```bash
# macOS
./gradlew :composeApp:packageDmg

# Windows
./gradlew :composeApp:packageMsi
```

---

## Troubleshooting

### Common Issues

1. **"Cannot find Database"**
   - Make sure `createDatabase()` is implemented in jvmMain
   - Check database path is writable

2. **"Koin not initialized"**
   - Ensure `startKoin` is called in main()
   - Check all modules are included

3. **"API key not found"**
   - Set environment variable
   - Or enter in Settings screen

4. **"Compose not rendering"**
   - Check all @Composable functions are properly annotated
   - Verify imports are correct

---

## Next Steps After MVP

1. **Add Global Hotkeys**
   - Implement JNA bindings
   - Register Ctrl/Cmd+B

2. **Add Screen Capture Hiding**
   - Implement platform-specific window properties
   - Test with Zoom, Meet, Teams

3. **Add More Problems**
   - Scrape LeetCode/HackerRank
   - Add 500+ problems

4. **Improve UI**
   - Add animations
   - Better error messages
   - Loading skeletons

5. **Add Tests**
   - Unit tests for all ViewModels
   - Integration tests for AI service
   - UI tests for screens

---

## Resources

- **MVP Plan**: `specs/mvp-implementation-plan.md` (complete code examples)
- **Full Plan**: `specs/implementation-plan.md` (future features)
- **User Guide**: `HOWTO.md`
- **Status**: `IMPLEMENTATION_STATUS.md`

---

## Getting Help

1. Check the MVP plan for code examples
2. Review existing implemented files
3. Check Compose Multiplatform docs
4. Ask questions in issues

---

**Good luck with the implementation! The foundation is solid - you just need to add the UI layer.**
