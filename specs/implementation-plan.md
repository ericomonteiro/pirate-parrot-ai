# Interview Assistant Application - Implementation Plan
## KMM Architecture - Ready for Implementation

## Executive Summary

This document provides a **concrete, decision-made implementation plan** for building a desktop application using **Kotlin Multiplatform (KMM)** - an AI-powered interview assistant that provides real-time coding assistance during technical interviews while remaining undetectable by browser-based monitoring systems.

**All architectural decisions have been finalized and are ready for implementation.**

### Quick Start Implementation Guide

**Technology Stack (FINAL)**:
- **Language**: Kotlin 2.0+
- **Framework**: Kotlin Multiplatform + Compose Multiplatform Desktop
- **Database**: SQLDelight 2.0+ with SQLite
- **AI**: OpenAI GPT-4 (primary), Claude 3.5 (fallback)
- **HTTP**: Ktor Client 2.3+
- **DI**: Koin 3.5+
- **Native**: JNA 5.14+
- **Build**: Gradle 8.5+ with Kotlin DSL

**Project Structure**:
```
composeApp/src/
├── commonMain/        # Shared: UI, domain, data, AI
├── desktopMain/       # Desktop-specific abstractions
├── macosMain/         # macOS native implementations
└── windowsMain/       # Windows native implementations
```

**Implementation Order**:
1. **Week 1-3**: Native window management, hotkeys, process hiding
2. **Week 4-7**: Database (SQLDelight), AI integration (OpenAI), UI (Compose)
3. **Week 8-11**: Audio (TTS), positioning, context detection
4. **Week 12-14**: Security audit, anti-detection testing
5. **Week 15-17**: UX polish, performance optimization
6. **Week 18-20**: Packaging, distribution, final testing

**Key Dependencies** (add to build.gradle.kts):
```kotlin
commonMain.dependencies {
    implementation("app.cash.sqldelight:coroutines-extensions:2.0.1")
    implementation("io.ktor:ktor-client-core:2.3.7")
    implementation("io.ktor:ktor-client-content-negotiation:2.3.7")
    implementation("io.ktor:ktor-serialization-kotlinx-json:2.3.7")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.6.2")
    implementation("io.insert-koin:koin-core:3.5.3")
    implementation("net.java.dev.jna:jna:5.14.0")
    implementation("net.java.dev.jna:jna-platform:5.14.0")
}

desktopMain.dependencies {
    implementation("app.cash.sqldelight:sqlite-driver:2.0.1")
    implementation("io.ktor:ktor-client-cio:2.3.7")
}
```

## Product Overview

### Core Concept
A native desktop application that:
- Provides real-time coding solutions during technical interviews
- Remains invisible to browser-based detection mechanisms
- Operates outside the browser sandbox as a separate native app
- Offers both visual and audio assistance
- Stays hidden from screen sharing and recording tools

### Key Value Propositions
1. **Undetectable by Design** - Native app architecture bypasses browser security sandbox
2. **Screen Share Invisible** - Uses OS-level APIs to exclude from screen captures
3. **No Focus Changes** - Global hotkeys maintain browser focus
4. **Task Manager Stealth** - Hidden from process lists
5. **Audio Support** - Real-time explanations and guidance
6. **Reasoning Support** - Line-by-line comments and thought process hints

---

## Technical Architecture

### 1. Platform & Architecture (DECIDED)

#### Target Platforms
- **macOS** (Primary - 60% of target users)
- **Windows** (Secondary - 35% of target users)
- **Linux** (Future - 5% of target users)

#### Technology Stack (FINAL)

**✅ SELECTED: Kotlin Multiplatform (KMM) + Compose Multiplatform Desktop**

This decision is final and implementation-ready.

#### Project Structure
```
copilot/
├── composeApp/
│   ├── src/
│   │   ├── commonMain/          # Shared business logic
│   │   │   ├── kotlin/
│   │   │   │   ├── data/        # Database, repositories
│   │   │   │   ├── domain/      # Use cases, models
│   │   │   │   ├── ui/          # Compose UI components
│   │   │   │   └── ai/          # AI service abstraction
│   │   │   └── resources/
│   │   ├── desktopMain/         # Desktop-specific code
│   │   │   └── kotlin/
│   │   │       ├── platform/    # Platform abstractions
│   │   │       └── native/      # JNA bindings
│   │   ├── macosMain/           # macOS-specific
│   │   │   └── kotlin/
│   │   │       └── native/      # NSWindow, hotkeys
│   │   ├── windowsMain/         # Windows-specific
│   │   │   └── kotlin/
│   │   │       └── native/      # Win32 API
│   │   └── jvmTest/
│   └── build.gradle.kts
├── shared/                       # Optional shared module
└── build.gradle.kts
```

---

## Core Features & Implementation Phases

### Phase 1: Foundation (Weeks 1-3)

#### 1.1 Native Window Management
**Objective**: Create a window that can be hidden from screen sharing

**Technical Implementation (CONCRETE)**:

**1. Create Platform Abstraction**
```kotlin
// commonMain/kotlin/platform/WindowManager.kt
expect class WindowManager {
    fun hideFromScreenCapture()
    fun setAlwaysOnTop(enabled: Boolean)
    fun setTransparency(alpha: Float)
    fun setClickThrough(enabled: Boolean)
    fun setPosition(x: Int, y: Int)
}
```

**2. macOS Implementation**
```kotlin
// macosMain/kotlin/platform/WindowManager.kt
import com.sun.jna.*
import com.sun.jna.platform.mac.CoreFoundation

actual class WindowManager {
    private val windowId: Long = getCurrentWindowId()
    
    actual fun hideFromScreenCapture() {
        // JNA call to NSWindow
        val nsWindow = getNSWindow(windowId)
        nsWindow.setSharingType(0) // NSWindowSharingNone
        nsWindow.setLevel(3) // NSFloatingWindowLevel
        nsWindow.setCollectionBehavior(128 or 256) // ignoresCycle | stationary
    }
    
    private external fun getNSWindow(id: Long): NSWindow
}

interface NSWindow : Library {
    fun setSharingType(type: Int)
    fun setLevel(level: Int)
    fun setCollectionBehavior(behavior: Int)
}
```

**3. Windows Implementation**
```kotlin
// windowsMain/kotlin/platform/WindowManager.kt
import com.sun.jna.*
import com.sun.jna.platform.win32.User32
import com.sun.jna.platform.win32.WinDef.HWND

actual class WindowManager {
    private val hwnd: HWND = getWindowHandle()
    
    actual fun hideFromScreenCapture() {
        val WDA_EXCLUDEFROMCAPTURE = 0x00000011
        User32.INSTANCE.SetWindowDisplayAffinity(hwnd, WDA_EXCLUDEFROMCAPTURE)
    }
}

interface User32Extended : User32 {
    fun SetWindowDisplayAffinity(hwnd: HWND, affinity: Int): Boolean
    companion object {
        val INSTANCE: User32Extended = Native.load("user32", User32Extended::class.java)
    }
}
```

**Implementation Tasks**:
- [ ] Set up JNA dependencies in build.gradle.kts
- [ ] Create expect/actual WindowManager classes
- [ ] Implement macOS native bindings (NSWindow)
- [ ] Implement Windows native bindings (User32)
- [ ] Create Compose window wrapper with platform integration
- [ ] Test screen capture exclusion on Zoom, Meet, Teams, Discord
- [ ] Implement transparency using Compose window properties
- [ ] Add click-through behavior using native window flags

#### 1.2 Global Hotkey System
**Objective**: Register system-wide keyboard shortcuts without stealing focus

**Technical Implementation (CONCRETE)**:

**1. Hotkey Abstraction**
```kotlin
// commonMain/kotlin/platform/HotkeyManager.kt
data class Hotkey(
    val modifiers: Set<KeyModifier>,
    val key: KeyCode,
    val action: () -> Unit
)

enum class KeyModifier { CTRL, ALT, SHIFT, META }
enum class KeyCode { B, UP, DOWN, LEFT, RIGHT, ESCAPE }

expect class HotkeyManager {
    fun registerHotkey(hotkey: Hotkey): Boolean
    fun unregisterHotkey(hotkey: Hotkey)
    fun unregisterAll()
}
```

**2. macOS Implementation**
```kotlin
// macosMain/kotlin/platform/HotkeyManager.kt
import com.sun.jna.*

actual class HotkeyManager {
    private val registeredHotkeys = mutableMapOf<Int, Hotkey>()
    private var nextId = 1
    
    actual fun registerHotkey(hotkey: Hotkey): Boolean {
        val id = nextId++
        val carbonModifiers = hotkey.modifiers.toCarbonModifiers()
        val carbonKeyCode = hotkey.key.toCarbonKeyCode()
        
        val success = CarbonLib.INSTANCE.RegisterEventHotKey(
            carbonKeyCode,
            carbonModifiers,
            id,
            null,
            0,
            null
        )
        
        if (success) {
            registeredHotkeys[id] = hotkey
            startEventLoop()
        }
        return success
    }
    
    private fun startEventLoop() {
        // Carbon event loop to handle hotkey events
        // Runs in background thread, doesn't steal focus
    }
}

interface CarbonLib : Library {
    fun RegisterEventHotKey(
        keyCode: Int,
        modifiers: Int,
        id: Int,
        target: Pointer?,
        options: Int,
        outRef: Pointer?
    ): Boolean
    
    companion object {
        val INSTANCE: CarbonLib = Native.load("Carbon", CarbonLib::class.java)
    }
}
```

**3. Windows Implementation**
```kotlin
// windowsMain/kotlin/platform/HotkeyManager.kt
import com.sun.jna.platform.win32.User32
import com.sun.jna.platform.win32.WinUser

actual class HotkeyManager {
    private val registeredHotkeys = mutableMapOf<Int, Hotkey>()
    private var nextId = 1
    
    actual fun registerHotkey(hotkey: Hotkey): Boolean {
        val id = nextId++
        val modifiers = hotkey.modifiers.toWin32Modifiers()
        val vkCode = hotkey.key.toVirtualKeyCode()
        
        val success = User32.INSTANCE.RegisterHotKey(
            null, // No window handle = global
            id,
            modifiers,
            vkCode
        )
        
        if (success) {
            registeredHotkeys[id] = hotkey
            startMessageLoop()
        }
        return success
    }
    
    private fun startMessageLoop() {
        // Win32 message loop in background thread
        // Handles WM_HOTKEY messages without stealing focus
    }
}
```

**Default Hotkeys**:
- `Cmd/Ctrl + B`: Toggle window visibility
- `Cmd/Ctrl + Shift + B`: Toggle stealth mode
- `Cmd/Ctrl + Arrow Keys`: Move window position
- `Cmd/Ctrl + Escape`: Emergency hide

**Implementation Tasks**:
- [ ] Add JNA dependency: `net.java.dev.jna:jna:5.14.0`
- [ ] Create expect/actual HotkeyManager classes
- [ ] Implement macOS Carbon hotkey registration
- [ ] Implement Windows RegisterHotKey API
- [ ] Create background event loop (coroutines)
- [ ] Add hotkey configuration UI in settings
- [ ] Implement hotkey conflict detection
- [ ] Test focus preservation with Chrome, Firefox, Safari, Edge
- [ ] Add hotkey customization persistence (SQLDelight)

#### 1.3 Process Hiding
**Objective**: Hide application from Task Manager/Activity Monitor

**Technical Implementation (CONCRETE)**:

**1. macOS Process Hiding**
```xml
<!-- macOS/Info.plist -->
<key>LSUIElement</key>
<string>1</string>
<key>LSBackgroundOnly</key>
<string>0</string>
<key>NSSupportsAutomaticGraphicsSwitching</key>
<true/>
```

```kotlin
// macosMain/kotlin/platform/ProcessManager.kt
import com.sun.jna.*

object ProcessManager {
    fun hideFromDock() {
        val nsApp = NSApplication.sharedApplication()
        nsApp.setActivationPolicy(1) // NSApplicationActivationPolicyAccessory
    }
    
    fun setProcessName(name: String) {
        val processInfo = NSProcessInfo.processInfo()
        processInfo.setProcessName(name)
    }
}

interface NSApplication : Library {
    fun sharedApplication(): NSApplication
    fun setActivationPolicy(policy: Int)
}
```

**2. Windows Process Hiding**
```kotlin
// windowsMain/kotlin/platform/ProcessManager.kt
import com.sun.jna.platform.win32.*

object ProcessManager {
    fun hideFromTaskbar() {
        val hwnd = getMainWindowHandle()
        // Remove from taskbar
        val exStyle = User32.INSTANCE.GetWindowLong(hwnd, WinUser.GWL_EXSTYLE)
        User32.INSTANCE.SetWindowLong(
            hwnd,
            WinUser.GWL_EXSTYLE,
            exStyle or WinUser.WS_EX_TOOLWINDOW and WinUser.WS_EX_APPWINDOW.inv()
        )
    }
    
    fun obfuscateProcessName() {
        // Rename process in memory (advanced technique)
        // Use JNA to call SetConsoleTitleW or modify PEB
        val randomName = generateRandomName()
        Kernel32.INSTANCE.SetConsoleTitleW(randomName)
    }
    
    private fun generateRandomName(): String {
        val commonNames = listOf(
            "System", "svchost", "RuntimeBroker",
            "SearchUI", "explorer", "dwm"
        )
        return commonNames.random() + ".exe"
    }
}
```

**3. Build Configuration**
```kotlin
// build.gradle.kts
compose.desktop {
    application {
        mainClass = "com.github.ericomonteiro.copilot.MainKt"
        
        nativeDistributions {
            targetFormats(Dmg, Msi, Deb)
            packageName = "InterviewAssistant"
            packageVersion = "1.0.0"
            
            macOS {
                bundleID = "com.github.ericomonteiro.copilot"
                infoPlist {
                    extraKeysRawXml = """
                        <key>LSUIElement</key>
                        <string>1</string>
                        <key>NSSupportsAutomaticGraphicsSwitching</key>
                        <true/>
                    """.trimIndent()
                }
            }
            
            windows {
                console = false // No console window
                dirChooser = false
                perUserInstall = true
            }
        }
    }
}
```

**Implementation Tasks**:
- [ ] Configure Info.plist with LSUIElement
- [ ] Implement NSApplication activation policy change
- [ ] Implement Windows taskbar hiding (WS_EX_TOOLWINDOW)
- [ ] Add process name obfuscation (optional, configurable)
- [ ] Create stealth mode toggle in settings
- [ ] Test with Activity Monitor (macOS) and Task Manager (Windows)
- [ ] Add development mode (visible in dock/taskbar)
- [ ] Document ethical considerations in README

---

### Phase 2: Core Functionality (Weeks 4-7)

#### 2.1 Code Solution Database
**Objective**: Store and retrieve coding solutions efficiently

**Technical Implementation (CONCRETE - SQLDelight)**:

**1. Database Schema (SQLDelight)**
```sql
-- commonMain/sqldelight/com/github/ericomonteiro/copilot/db/Database.sq

CREATE TABLE Problem (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    platform TEXT NOT NULL,
    problem_name TEXT NOT NULL,
    problem_number INTEGER,
    difficulty TEXT NOT NULL, -- Easy, Medium, Hard
    category TEXT NOT NULL, -- Array, String, DP, etc.
    tags TEXT NOT NULL, -- JSON array
    description TEXT NOT NULL,
    constraints TEXT,
    examples TEXT, -- JSON array
    hints TEXT, -- JSON array
    created_at INTEGER NOT NULL,
    updated_at INTEGER NOT NULL,
    frequency INTEGER DEFAULT 0, -- Usage frequency
    success_rate REAL DEFAULT 0.0
);

CREATE INDEX idx_problem_platform ON Problem(platform);
CREATE INDEX idx_problem_difficulty ON Problem(difficulty);
CREATE INDEX idx_problem_category ON Problem(category);
CREATE INDEX idx_problem_name ON Problem(problem_name);

CREATE TABLE Solution (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    problem_id INTEGER NOT NULL,
    language TEXT NOT NULL, -- kotlin, java, python, cpp, javascript
    code TEXT NOT NULL,
    explanation TEXT NOT NULL,
    time_complexity TEXT NOT NULL,
    space_complexity TEXT NOT NULL,
    reasoning_hints TEXT NOT NULL, -- JSON array
    approach_name TEXT, -- "Two Pointers", "Sliding Window", etc.
    is_optimal INTEGER DEFAULT 1, -- Boolean
    created_at INTEGER NOT NULL,
    FOREIGN KEY (problem_id) REFERENCES Problem(id) ON DELETE CASCADE
);

CREATE INDEX idx_solution_problem ON Solution(problem_id);
CREATE INDEX idx_solution_language ON Solution(language);

CREATE TABLE TestCase (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    problem_id INTEGER NOT NULL,
    input TEXT NOT NULL,
    expected_output TEXT NOT NULL,
    explanation TEXT,
    is_example INTEGER DEFAULT 0, -- Boolean
    FOREIGN KEY (problem_id) REFERENCES Problem(id) ON DELETE CASCADE
);

CREATE TABLE UserSettings (
    key TEXT PRIMARY KEY,
    value TEXT NOT NULL
);

CREATE TABLE SearchHistory (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    query TEXT NOT NULL,
    timestamp INTEGER NOT NULL
);

CREATE TABLE RecentProblems (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    problem_id INTEGER NOT NULL,
    accessed_at INTEGER NOT NULL,
    FOREIGN KEY (problem_id) REFERENCES Problem(id) ON DELETE CASCADE
);

-- Queries
selectAllProblems:
SELECT * FROM Problem ORDER BY created_at DESC;

selectProblemById:
SELECT * FROM Problem WHERE id = ?;

searchProblems:
SELECT * FROM Problem 
WHERE problem_name LIKE '%' || ? || '%'
   OR description LIKE '%' || ? || '%'
ORDER BY frequency DESC
LIMIT ?;

filterProblems:
SELECT * FROM Problem
WHERE (:platform IS NULL OR platform = :platform)
  AND (:difficulty IS NULL OR difficulty = :difficulty)
  AND (:category IS NULL OR category = :category)
ORDER BY created_at DESC;

selectSolutionsByProblem:
SELECT * FROM Solution WHERE problem_id = ?;

selectSolutionByLanguage:
SELECT * FROM Solution 
WHERE problem_id = ? AND language = ?
ORDER BY is_optimal DESC
LIMIT 1;

insertProblem:
INSERT INTO Problem(platform, problem_name, problem_number, difficulty, category, tags, description, constraints, examples, hints, created_at, updated_at)
VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);

insertSolution:
INSERT INTO Solution(problem_id, language, code, explanation, time_complexity, space_complexity, reasoning_hints, approach_name, is_optimal, created_at)
VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?);

updateProblemFrequency:
UPDATE Problem SET frequency = frequency + 1 WHERE id = ?;

insertRecentProblem:
INSERT INTO RecentProblems(problem_id, accessed_at) VALUES (?, ?);

selectRecentProblems:
SELECT p.* FROM Problem p
INNER JOIN RecentProblems r ON p.id = r.problem_id
ORDER BY r.accessed_at DESC
LIMIT 10;
```

**2. Repository Implementation**
```kotlin
// commonMain/kotlin/data/repository/ProblemRepository.kt
class ProblemRepository(private val database: Database) {
    private val queries = database.databaseQueries
    
    suspend fun searchProblems(query: String, limit: Long = 50): List<Problem> = 
        withContext(Dispatchers.IO) {
            queries.searchProblems(query, query, limit).executeAsList()
        }
    
    suspend fun getProblemWithSolutions(
        problemId: Long,
        language: String
    ): ProblemWithSolution? = withContext(Dispatchers.IO) {
        val problem = queries.selectProblemById(problemId).executeAsOneOrNull()
        val solution = queries.selectSolutionByLanguage(problemId, language).executeAsOneOrNull()
        
        if (problem != null && solution != null) {
            queries.updateProblemFrequency(problemId)
            queries.insertRecentProblem(problemId, Clock.System.now().toEpochMilliseconds())
            ProblemWithSolution(problem, solution)
        } else null
    }
    
    suspend fun filterProblems(
        platform: String?,
        difficulty: String?,
        category: String?
    ): List<Problem> = withContext(Dispatchers.IO) {
        queries.filterProblems(platform, difficulty, category).executeAsList()
    }
}
```

**3. Build Configuration**
```kotlin
// build.gradle.kts
plugins {
    id("app.cash.sqldelight") version "2.0.1"
}

sqldelight {
    databases {
        create("Database") {
            packageName.set("com.github.ericomonteiro.copilot.db")
            dialect("app.cash.sqldelight:sqlite-3-38-dialect:2.0.1")
        }
    }
}

dependencies {
    commonMain.dependencies {
        implementation("app.cash.sqldelight:coroutines-extensions:2.0.1")
    }
    desktopMain.dependencies {
        implementation("app.cash.sqldelight:sqlite-driver:2.0.1")
    }
}
```

**Implementation Tasks**:
- [ ] Add SQLDelight plugin to build.gradle.kts
- [ ] Create Database.sq schema file
- [ ] Generate SQLDelight code
- [ ] Implement ProblemRepository
- [ ] Implement SolutionRepository
- [ ] Create database initialization code
- [ ] Add migration support
- [ ] Implement in-memory caching (Map with LRU)
- [ ] Create data seeding script (import problems)
- [ ] Add database backup/restore functionality

#### 2.2 AI Integration
**Objective**: Generate solutions and explanations using AI

**Technical Implementation (CONCRETE - Ktor + OpenAI)**:

**1. AI Service Abstraction**
```kotlin
// commonMain/kotlin/ai/AIService.kt
interface AIService {
    suspend fun generateSolution(request: SolutionRequest): Result<SolutionResponse>
    fun generateSolutionStream(request: SolutionRequest): Flow<SolutionChunk>
    suspend fun explainCode(code: String, language: String): Result<String>
    suspend fun generateReasoningHints(problem: String): Result<List<String>>
}

data class SolutionRequest(
    val problemDescription: String,
    val constraints: String,
    val examples: List<Example>,
    val targetLanguage: String,
    val includeExplanation: Boolean = true,
    val includeComplexity: Boolean = true
)

data class SolutionResponse(
    val code: String,
    val explanation: String,
    val timeComplexity: String,
    val spaceComplexity: String,
    val reasoningHints: List<String>,
    val approach: String
)
```

**2. OpenAI Implementation**
```kotlin
// commonMain/kotlin/ai/OpenAIService.kt
class OpenAIService(
    private val apiKey: String,
    private val httpClient: HttpClient
) : AIService {
    
    private val baseUrl = "https://api.openai.com/v1"
    private val model = "gpt-4-turbo-preview"
    
    override suspend fun generateSolution(
        request: SolutionRequest
    ): Result<SolutionResponse> = runCatching {
        val prompt = buildSolutionPrompt(request)
        
        val response = httpClient.post("$baseUrl/chat/completions") {
            contentType(ContentType.Application.Json)
            header("Authorization", "Bearer $apiKey")
            setBody(OpenAIRequest(
                model = model,
                messages = listOf(
                    Message("system", SYSTEM_PROMPT),
                    Message("user", prompt)
                ),
                temperature = 0.3,
                maxTokens = 2000
            ))
        }.body<OpenAIResponse>()
        
        parseSolutionResponse(response.choices.first().message.content)
    }
    
    override fun generateSolutionStream(
        request: SolutionRequest
    ): Flow<SolutionChunk> = flow {
        val prompt = buildSolutionPrompt(request)
        
        httpClient.preparePost("$baseUrl/chat/completions") {
            contentType(ContentType.Application.Json)
            header("Authorization", "Bearer $apiKey")
            setBody(OpenAIRequest(
                model = model,
                messages = listOf(
                    Message("system", SYSTEM_PROMPT),
                    Message("user", prompt)
                ),
                temperature = 0.3,
                stream = true
            ))
        }.execute { response ->
            response.bodyAsChannel().readUTF8Line()?.let { line ->
                if (line.startsWith("data: ")) {
                    val data = line.removePrefix("data: ")
                    if (data != "[DONE]") {
                        val chunk = Json.decodeFromString<OpenAIStreamChunk>(data)
                        emit(SolutionChunk(chunk.choices.first().delta.content))
                    }
                }
            }
        }
    }
    
    private fun buildSolutionPrompt(request: SolutionRequest): String = """
        Generate a complete solution for the following coding problem:
        
        Problem: ${request.problemDescription}
        
        Constraints: ${request.constraints}
        
        Examples:
        ${request.examples.joinToString("\n") { "Input: ${it.input}\nOutput: ${it.output}" }}
        
        Requirements:
        - Language: ${request.targetLanguage}
        - Include detailed explanation
        - Provide time and space complexity analysis
        - Add reasoning hints for interview discussion
        - Use optimal approach
        
        Format your response as JSON:
        {
          "code": "...",
          "explanation": "...",
          "timeComplexity": "O(...)",
          "spaceComplexity": "O(...)",
          "reasoningHints": ["..."],
          "approach": "..."
        }
    """.trimIndent()
    
    companion object {
        private const val SYSTEM_PROMPT = """
            You are an expert software engineer specializing in technical interviews.
            Generate optimal, clean, and well-documented code solutions.
            Provide clear explanations suitable for interview discussions.
        """
    }
}

@Serializable
data class OpenAIRequest(
    val model: String,
    val messages: List<Message>,
    val temperature: Double = 0.7,
    @SerialName("max_tokens") val maxTokens: Int? = null,
    val stream: Boolean = false
)

@Serializable
data class Message(val role: String, val content: String)

@Serializable
data class OpenAIResponse(
    val choices: List<Choice>
)

@Serializable
data class Choice(val message: Message)
```

**3. Anthropic Claude Fallback**
```kotlin
// commonMain/kotlin/ai/ClaudeService.kt
class ClaudeService(
    private val apiKey: String,
    private val httpClient: HttpClient
) : AIService {
    
    private val baseUrl = "https://api.anthropic.com/v1"
    private val model = "claude-3-5-sonnet-20241022"
    
    override suspend fun generateSolution(
        request: SolutionRequest
    ): Result<SolutionResponse> = runCatching {
        val prompt = buildSolutionPrompt(request)
        
        val response = httpClient.post("$baseUrl/messages") {
            contentType(ContentType.Application.Json)
            header("x-api-key", apiKey)
            header("anthropic-version", "2023-06-01")
            setBody(ClaudeRequest(
                model = model,
                maxTokens = 2000,
                messages = listOf(ClaudeMessage("user", prompt))
            ))
        }.body<ClaudeResponse>()
        
        parseSolutionResponse(response.content.first().text)
    }
}
```

**4. Ktor Client Configuration**
```kotlin
// commonMain/kotlin/ai/HttpClientFactory.kt
object HttpClientFactory {
    fun create(): HttpClient = HttpClient {
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
                isLenient = true
            })
        }
        install(HttpTimeout) {
            requestTimeoutMillis = 60000
            connectTimeoutMillis = 10000
        }
        install(HttpRequestRetry) {
            retryOnServerErrors(maxRetries = 3)
            exponentialDelay()
        }
        install(Logging) {
            logger = Logger.DEFAULT
            level = LogLevel.INFO
        }
    }
}
```

**5. Dependency Injection (Koin)**
```kotlin
// commonMain/kotlin/di/AppModule.kt
val appModule = module {
    single { HttpClientFactory.create() }
    
    single<AIService> {
        val apiKey = get<UserSettings>().openAIApiKey
        OpenAIService(apiKey, get())
    }
    
    single { ProblemRepository(get()) }
}
```

**6. Build Dependencies**
```kotlin
// build.gradle.kts
dependencies {
    commonMain.dependencies {
        implementation("io.ktor:ktor-client-core:2.3.7")
        implementation("io.ktor:ktor-client-content-negotiation:2.3.7")
        implementation("io.ktor:ktor-serialization-kotlinx-json:2.3.7")
        implementation("io.ktor:ktor-client-logging:2.3.7")
        implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.6.2")
        implementation("io.insert-koin:koin-core:3.5.3")
    }
    
    desktopMain.dependencies {
        implementation("io.ktor:ktor-client-cio:2.3.7")
    }
}
```

**Implementation Tasks**:
- [ ] Add Ktor dependencies to build.gradle.kts
- [ ] Create AIService interface
- [ ] Implement OpenAIService with GPT-4
- [ ] Implement ClaudeService as fallback
- [ ] Create prompt engineering templates
- [ ] Add streaming support for real-time generation
- [ ] Implement retry logic with exponential backoff
- [ ] Add response caching (in-memory + SQLDelight)
- [ ] Create API key management UI
- [ ] Add usage tracking and rate limiting
- [ ] Implement local LLM support (Ollama) - optional

#### 2.3 User Interface
**Objective**: Create an intuitive, minimal UI for solution display

**Technical Implementation (CONCRETE - Compose Multiplatform)**:

**1. Main Application Structure**
```kotlin
// commonMain/kotlin/ui/App.kt
@Composable
fun App() {
    val viewModel: MainViewModel = koinViewModel()
    val uiState by viewModel.uiState.collectAsState()
    
    MaterialTheme(
        colorScheme = if (uiState.isDarkTheme) darkColorScheme() else lightColorScheme()
    ) {
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .alpha(uiState.opacity),
            color = Color.Transparent
        ) {
            when (uiState.currentScreen) {
                Screen.Search -> SearchScreen()
                Screen.Solution -> SolutionScreen()
                Screen.Settings -> SettingsScreen()
            }
        }
    }
}
```

**2. Search Screen**
```kotlin
// commonMain/kotlin/ui/search/SearchScreen.kt
@Composable
fun SearchScreen() {
    val viewModel: SearchViewModel = koinViewModel()
    val searchState by viewModel.searchState.collectAsState()
    
    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        // Search Bar
        OutlinedTextField(
            value = searchState.query,
            onValueChange = { viewModel.onSearchQueryChanged(it) },
            modifier = Modifier.fillMaxWidth(),
            placeholder = { Text("Search problems...") },
            leadingIcon = { Icon(Icons.Default.Search, null) },
            singleLine = true
        )
        
        Spacer(modifier = Modifier.height(8.dp))
        
        // Filters
        Row(modifier = Modifier.fillMaxWidth()) {
            FilterChip(
                selected = searchState.selectedPlatform != null,
                onClick = { viewModel.showPlatformFilter() },
                label = { Text(searchState.selectedPlatform ?: "Platform") }
            )
            Spacer(modifier = Modifier.width(8.dp))
            FilterChip(
                selected = searchState.selectedDifficulty != null,
                onClick = { viewModel.showDifficultyFilter() },
                label = { Text(searchState.selectedDifficulty ?: "Difficulty") }
            )
        }
        
        Spacer(modifier = Modifier.height(16.dp))
        
        // Results
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(searchState.results) { problem ->
                ProblemCard(
                    problem = problem,
                    onClick = { viewModel.selectProblem(problem.id) }
                )
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
        Column(modifier = Modifier.padding(12.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = problem.problemName,
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.weight(1f)
                )
                DifficultyBadge(problem.difficulty)
            }
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "${problem.platform} • ${problem.category}",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}
```

**3. Solution Display Screen**
```kotlin
// commonMain/kotlin/ui/solution/SolutionScreen.kt
@Composable
fun SolutionScreen() {
    val viewModel: SolutionViewModel = koinViewModel()
    val solutionState by viewModel.solutionState.collectAsState()
    
    Column(modifier = Modifier.fillMaxSize()) {
        // Header with language selector
        Row(
            modifier = Modifier.fillMaxWidth().padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = solutionState.problem?.problemName ?: "",
                style = MaterialTheme.typography.titleLarge
            )
            
            LanguageSelector(
                selectedLanguage = solutionState.selectedLanguage,
                onLanguageSelected = { viewModel.changeLanguage(it) }
            )
        }
        
        // Tabbed content
        TabRow(selectedTabIndex = solutionState.selectedTab) {
            Tab(
                selected = solutionState.selectedTab == 0,
                onClick = { viewModel.selectTab(0) },
                text = { Text("Code") }
            )
            Tab(
                selected = solutionState.selectedTab == 1,
                onClick = { viewModel.selectTab(1) },
                text = { Text("Explanation") }
            )
            Tab(
                selected = solutionState.selectedTab == 2,
                onClick = { viewModel.selectTab(2) },
                text = { Text("Hints") }
            )
        }
        
        // Content
        when (solutionState.selectedTab) {
            0 -> CodeView(code = solutionState.solution?.code ?: "")
            1 -> ExplanationView(explanation = solutionState.solution?.explanation ?: "")
            2 -> HintsView(hints = solutionState.solution?.reasoningHints ?: emptyList())
        }
    }
}

@Composable
fun CodeView(code: String) {
    val scrollState = rememberScrollState()
    
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF1E1E1E))
            .padding(16.dp)
    ) {
        SelectionContainer {
            Text(
                text = code,
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontFamily = FontFamily.Monospace,
                    color = Color(0xFFD4D4D4)
                ),
                modifier = Modifier.verticalScroll(scrollState)
            )
        }
    }
}
```

**4. Settings Screen**
```kotlin
// commonMain/kotlin/ui/settings/SettingsScreen.kt
@Composable
fun SettingsScreen() {
    val viewModel: SettingsViewModel = koinViewModel()
    val settings by viewModel.settings.collectAsState()
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        // Appearance
        SettingsSection(title = "Appearance") {
            SwitchSetting(
                title = "Dark Theme",
                checked = settings.isDarkTheme,
                onCheckedChange = { viewModel.setDarkTheme(it) }
            )
            
            SliderSetting(
                title = "Opacity",
                value = settings.opacity,
                onValueChange = { viewModel.setOpacity(it) },
                valueRange = 0.3f..1.0f
            )
        }
        
        // Hotkeys
        SettingsSection(title = "Hotkeys") {
            HotkeySetting(
                title = "Toggle Visibility",
                hotkey = settings.toggleHotkey,
                onHotkeyChange = { viewModel.setToggleHotkey(it) }
            )
        }
        
        // AI Configuration
        SettingsSection(title = "AI Provider") {
            DropdownSetting(
                title = "Provider",
                options = listOf("OpenAI", "Claude", "Local LLM"),
                selected = settings.aiProvider,
                onSelect = { viewModel.setAIProvider(it) }
            )
            
            PasswordTextField(
                title = "API Key",
                value = settings.apiKey,
                onValueChange = { viewModel.setAPIKey(it) }
            )
        }
        
        // Stealth Mode
        SettingsSection(title = "Stealth") {
            SwitchSetting(
                title = "Hide from Screen Capture",
                checked = settings.hideFromCapture,
                onCheckedChange = { viewModel.setHideFromCapture(it) }
            )
            
            SwitchSetting(
                title = "Hide from Task Manager",
                checked = settings.hideFromTaskManager,
                onCheckedChange = { viewModel.setHideFromTaskManager(it) }
            )
        }
    }
}
```

**5. ViewModel Architecture**
```kotlin
// commonMain/kotlin/ui/MainViewModel.kt
class MainViewModel(
    private val problemRepository: ProblemRepository,
    private val settingsRepository: SettingsRepository
) : ViewModel() {
    
    private val _uiState = MutableStateFlow(UiState())
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()
    
    fun navigateTo(screen: Screen) {
        _uiState.update { it.copy(currentScreen = screen) }
    }
}

data class UiState(
    val currentScreen: Screen = Screen.Search,
    val isDarkTheme: Boolean = true,
    val opacity: Float = 0.95f
)

enum class Screen { Search, Solution, Settings }
```

**Implementation Tasks**:
- [ ] Set up Compose Multiplatform dependencies
- [ ] Create base UI structure (App.kt)
- [ ] Implement SearchScreen with filtering
- [ ] Create SolutionScreen with tabs
- [ ] Build SettingsScreen with all configurations
- [ ] Implement ViewModels with StateFlow
- [ ] Add syntax highlighting for code view
- [ ] Create custom theme (dark/light)
- [ ] Implement keyboard navigation (Tab, Arrow keys)
- [ ] Add animations and transitions
- [ ] Test UI responsiveness and performance

---

### Phase 3: Advanced Features (Weeks 8-11)

#### 3.1 Audio Support System
**Objective**: Provide audio explanations and guidance

**Technical Implementation (CONCRETE)**:

**1. Audio Service Abstraction**
```kotlin
// commonMain/kotlin/audio/AudioService.kt
interface AudioService {
    suspend fun speak(text: String, options: SpeechOptions = SpeechOptions())
    fun pause()
    fun resume()
    fun stop()
    fun setRate(rate: Float) // 0.5 to 2.0
    fun setVolume(volume: Float) // 0.0 to 1.0
}

data class SpeechOptions(
    val rate: Float = 1.0f,
    val volume: Float = 1.0f,
    val voice: String? = null
)
```

**2. macOS TTS Implementation**
```kotlin
// macosMain/kotlin/audio/MacOSAudioService.kt
import com.sun.jna.*

actual class AudioService {
    private val synthesizer = AVSpeechSynthesizer.create()
    
    actual suspend fun speak(text: String, options: SpeechOptions) {
        val utterance = AVSpeechUtterance.create(text)
        utterance.setRate(options.rate)
        utterance.setVolume(options.volume)
        
        if (options.voice != null) {
            val voice = AVSpeechSynthesisVoice.voiceWithIdentifier(options.voice)
            utterance.setVoice(voice)
        }
        
        synthesizer.speakUtterance(utterance)
    }
    
    actual fun pause() {
        synthesizer.pauseSpeaking()
    }
    
    actual fun stop() {
        synthesizer.stopSpeaking()
    }
}

interface AVSpeechSynthesizer : Library {
    fun create(): AVSpeechSynthesizer
    fun speakUtterance(utterance: AVSpeechUtterance)
    fun pauseSpeaking()
    fun stopSpeaking()
    
    companion object {
        val INSTANCE: AVSpeechSynthesizer = 
            Native.load("AVFoundation", AVSpeechSynthesizer::class.java)
    }
}
```

**3. Windows TTS Implementation**
```kotlin
// windowsMain/kotlin/audio/WindowsAudioService.kt
import com.sun.jna.*
import com.sun.jna.platform.win32.COM.COMUtils
import com.sun.jna.platform.win32.Ole32

actual class AudioService {
    private val voice: ISpVoice
    
    init {
        Ole32.INSTANCE.CoInitialize(null)
        voice = COMUtils.createInstance(ISpVoice::class.java)
    }
    
    actual suspend fun speak(text: String, options: SpeechOptions) {
        voice.SetRate((options.rate * 10 - 10).toInt()) // -10 to 10
        voice.SetVolume((options.volume * 100).toInt()) // 0 to 100
        voice.Speak(text, 0)
    }
    
    actual fun pause() {
        voice.Pause()
    }
    
    actual fun stop() {
        voice.Skip("SENTENCE", Int.MAX_VALUE)
    }
}

interface ISpVoice : Library {
    fun Speak(text: String, flags: Int): Int
    fun Pause(): Int
    fun Resume(): Int
    fun Skip(itemType: String, numItems: Int): Int
    fun SetRate(rate: Int): Int
    fun SetVolume(volume: Int): Int
}
```

**4. Audio Explanation Generator**
```kotlin
// commonMain/kotlin/audio/ExplanationGenerator.kt
class ExplanationGenerator(private val aiService: AIService) {
    
    suspend fun generateAudioExplanation(
        solution: Solution,
        type: ExplanationType
    ): String = when (type) {
        ExplanationType.OVERVIEW -> generateOverview(solution)
        ExplanationType.STEP_BY_STEP -> generateStepByStep(solution)
        ExplanationType.COMPLEXITY -> generateComplexityExplanation(solution)
        ExplanationType.HINTS -> generateHints(solution)
    }
    
    private suspend fun generateOverview(solution: Solution): String {
        return """
            Here's the solution approach: ${solution.approach}.
            
            The time complexity is ${solution.timeComplexity},
            and space complexity is ${solution.spaceComplexity}.
            
            Let me explain the key steps.
        """.trimIndent()
    }
    
    private suspend fun generateStepByStep(solution: Solution): String {
        val lines = solution.code.lines()
        return buildString {
            appendLine("Let me walk through the code step by step.")
            solution.reasoningHints.forEachIndexed { index, hint ->
                appendLine("Step ${index + 1}: $hint")
            }
        }
    }
}

enum class ExplanationType {
    OVERVIEW, STEP_BY_STEP, COMPLEXITY, HINTS
}
```

**5. Audio UI Controls**
```kotlin
// commonMain/kotlin/ui/audio/AudioControls.kt
@Composable
fun AudioControls() {
    val viewModel: AudioViewModel = koinViewModel()
    val audioState by viewModel.audioState.collectAsState()
    
    Row(
        modifier = Modifier.fillMaxWidth().padding(8.dp),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        IconButton(onClick = { viewModel.playOverview() }) {
            Icon(Icons.Default.PlayArrow, "Play Overview")
        }
        
        IconButton(onClick = { viewModel.playStepByStep() }) {
            Icon(Icons.Default.List, "Step by Step")
        }
        
        IconButton(
            onClick = { if (audioState.isPlaying) viewModel.pause() else viewModel.resume() }
        ) {
            Icon(
                if (audioState.isPlaying) Icons.Default.Pause else Icons.Default.PlayArrow,
                "Pause/Resume"
            )
        }
        
        IconButton(onClick = { viewModel.stop() }) {
            Icon(Icons.Default.Stop, "Stop")
        }
    }
    
    // Speed control
    Row(verticalAlignment = Alignment.CenterVertically) {
        Text("Speed: ${audioState.rate}x")
        Slider(
            value = audioState.rate,
            onValueChange = { viewModel.setRate(it) },
            valueRange = 0.5f..2.0f
        )
    }
}
```

**Implementation Tasks**:
- [ ] Add JNA audio bindings for macOS (AVFoundation)
- [ ] Add JNA audio bindings for Windows (SAPI)
- [ ] Create expect/actual AudioService
- [ ] Implement ExplanationGenerator
- [ ] Create audio UI controls
- [ ] Add voice selection UI
- [ ] Implement audio queue for multiple explanations
- [ ] Test audio during screen sharing (should work)
- [ ] Add audio transcription display
- [ ] Implement ElevenLabs cloud TTS fallback

#### 3.2 Screen Position Intelligence
**Objective**: Smart positioning over coding areas

**Technical Implementation (CONCRETE)**:

**1. Window Positioning Service**
```kotlin
// commonMain/kotlin/positioning/PositioningService.kt
class PositioningService(private val windowManager: WindowManager) {
    
    fun positionOverCodeEditor(platform: InterviewPlatform) {
        val position = getOptimalPosition(platform)
        windowManager.setPosition(position.x, position.y)
        windowManager.setSize(position.width, position.height)
    }
    
    private fun getOptimalPosition(platform: InterviewPlatform): WindowPosition {
        return when (platform) {
            InterviewPlatform.LEETCODE -> WindowPosition(
                x = 100, y = 200, width = 600, height = 400
            )
            InterviewPlatform.HACKERRANK -> WindowPosition(
                x = 150, y = 250, width = 550, height = 450
            )
            else -> WindowPosition(
                x = 100, y = 200, width = 600, height = 400
            )
        }
    }
}

enum class InterviewPlatform {
    LEETCODE, HACKERRANK, CODESIGNAL, CODERBYTE, GENERIC
}
```

**Implementation Tasks**:
- [ ] Create PositioningService with platform presets
- [ ] Add manual position adjustment with arrow keys
- [ ] Save position preferences per platform (SQLDelight)
- [ ] Handle multi-monitor setups
- [ ] Add position reset functionality
- [ ] Test positioning on different screen resolutions

#### 3.3 Context Detection
**Objective**: Automatically detect interview platform and problem

**Technical Implementation (CONCRETE)**:

**1. Clipboard Monitoring (Simple Approach)**
```kotlin
// commonMain/kotlin/detection/ClipboardMonitor.kt
class ClipboardMonitor {
    private val clipboard = Toolkit.getDefaultToolkit().systemClipboard
    
    fun startMonitoring(onTextDetected: (String) -> Unit) {
        CoroutineScope(Dispatchers.IO).launch {
            var lastText = ""
            while (isActive) {
                try {
                    val contents = clipboard.getContents(null)
                    if (contents?.isDataFlavorSupported(DataFlavor.stringFlavor) == true) {
                        val text = contents.getTransferData(DataFlavor.stringFlavor) as String
                        if (text != lastText && text.length > 20) {
                            lastText = text
                            onTextDetected(text)
                        }
                    }
                } catch (e: Exception) {
                    // Ignore clipboard access errors
                }
                delay(1000) // Check every second
            }
        }
    }
}
```

**2. Problem Matcher**
```kotlin
// commonMain/kotlin/detection/ProblemMatcher.kt
class ProblemMatcher(private val repository: ProblemRepository) {
    
    suspend fun findMatchingProblem(text: String): Problem? {
        // Extract problem title from text
        val title = extractProblemTitle(text)
        
        // Search in database
        val results = repository.searchProblems(title, limit = 5)
        
        // Return best match using similarity score
        return results.maxByOrNull { calculateSimilarity(it.problemName, title) }
    }
    
    private fun extractProblemTitle(text: String): String {
        // Simple extraction: first line or first sentence
        return text.lines().firstOrNull()?.take(100) ?: text.take(100)
    }
    
    private fun calculateSimilarity(s1: String, s2: String): Double {
        // Levenshtein distance or simple word matching
        val words1 = s1.lowercase().split(" ").toSet()
        val words2 = s2.lowercase().split(" ").toSet()
        val intersection = words1.intersect(words2).size
        val union = words1.union(words2).size
        return if (union > 0) intersection.toDouble() / union else 0.0
    }
}
```

**Implementation Tasks**:
- [ ] Implement ClipboardMonitor
- [ ] Create ProblemMatcher with similarity algorithm
- [ ] Add auto-suggestion notification
- [ ] Implement manual problem selection override
- [ ] Test accuracy with real interview problems
- [ ] Add platform detection from clipboard text patterns

#### 3.4 Reasoning & Explanation Generator
**Objective**: Generate natural explanations for solutions

**Technical Implementation (CONCRETE)**:

**Already implemented in AI Integration section (2.2)**

Additional features:
```kotlin
// commonMain/kotlin/ai/ReasoningService.kt
class ReasoningService(private val aiService: AIService) {
    
    suspend fun generateInterviewTips(problem: Problem, solution: Solution): List<String> {
        val prompt = """
            Generate 5 interview tips for discussing this solution:
            Problem: ${problem.problemName}
            Approach: ${solution.approach}
            
            Tips should help the candidate:
            1. Explain their thought process
            2. Discuss trade-offs
            3. Handle follow-up questions
            4. Demonstrate problem-solving skills
            5. Show optimization awareness
        """.trimIndent()
        
        return aiService.generateReasoningHints(prompt).getOrElse { emptyList() }
    }
    
    suspend fun generateFollowUpQuestions(solution: Solution): List<String> {
        val prompt = """
            Generate 3 common follow-up questions an interviewer might ask about this solution:
            ${solution.explanation}
        """.trimIndent()
        
        return aiService.generateReasoningHints(prompt).getOrElse { emptyList() }
    }
}
```

**Implementation Tasks**:
- [ ] Create ReasoningService
- [ ] Generate interview tips for each solution
- [ ] Create follow-up question generator
- [ ] Add "explain this line" feature (AI-powered)
- [ ] Test naturalness of explanations with users
- [ ] Cache generated explanations

---

### Phase 4: Security & Stealth (Weeks 12-14)

#### 4.1 Anti-Detection Measures

**Browser Sandbox Bypass**:
- ✅ Already achieved by being a native app
- Ensure no browser extensions or plugins required
- No communication with browser processes

**Screen Sharing Invisibility**:
- [ ] Test on all major platforms (Zoom, Meet, Teams, Discord)
- [ ] Verify window exclusion flags work
- [ ] Test with different screen sharing modes (window vs. full screen)
- [ ] Handle edge cases (screenshots, recording software)

**Process Hiding**:
- [ ] Randomize process names
- [ ] Hide from Activity Monitor/Task Manager
- [ ] Prevent dock/taskbar appearance
- [ ] Test with process monitoring tools

**Network Traffic**:
- [ ] Use HTTPS for all API calls
- [ ] Implement traffic obfuscation (optional)
- [ ] Add proxy support
- [ ] Minimize network fingerprint

**Implementation Tasks**:
- [ ] Conduct security audit
- [ ] Test detection methods used by interview platforms
- [ ] Implement additional obfuscation if needed
- [ ] Create detection testing suite
- [ ] Document security measures

#### 4.2 Focus Management
**Objective**: Never steal focus from browser

**Implementation Tasks**:
- [ ] Implement focus-less window updates
- [ ] Test with all major browsers
- [ ] Verify no `blur` events triggered
- [ ] Handle edge cases (Alt+Tab, Mission Control)

---

### Phase 5: User Experience & Polish (Weeks 15-17)

#### 5.1 Onboarding & Tutorial
- [ ] Create first-run setup wizard
- [ ] Build interactive tutorial
- [ ] Add demo mode with sample problems
- [ ] Create video tutorials
- [ ] Write comprehensive documentation

#### 5.2 Performance Optimization
- [ ] Optimize startup time
- [ ] Reduce memory footprint
- [ ] Implement lazy loading
- [ ] Add performance monitoring
- [ ] Profile and optimize hot paths

#### 5.3 Error Handling & Logging
- [ ] Implement comprehensive error handling
- [ ] Add user-friendly error messages
- [ ] Create logging system (with privacy considerations)
- [ ] Build crash reporting (optional, privacy-focused)
- [ ] Add diagnostic tools

#### 5.4 Updates & Maintenance
- [ ] Implement auto-update system
- [ ] Create update notification UI
- [ ] Build rollback mechanism
- [ ] Add telemetry (optional, privacy-focused)
- [ ] Create feedback system

---

### Phase 6: Platform Support & Distribution (Weeks 18-20)

#### 6.1 Platform-Specific Features

**macOS**:
- [ ] Code signing and notarization
- [ ] Gatekeeper compatibility
- [ ] Touch Bar support (optional)
- [ ] Native menu bar integration

**Windows**:
- [ ] Code signing certificate
- [ ] Windows Defender compatibility
- [ ] System tray integration
- [ ] Windows 10/11 specific features

**Linux** (Optional):
- [ ] AppImage/Flatpak/Snap packaging
- [ ] Desktop entry files
- [ ] Wayland/X11 compatibility

#### 6.2 Distribution

**Packaging**:
- [ ] Create installers (DMG, MSI, etc.)
- [ ] Implement auto-updater
- [ ] Set up distribution server
- [ ] Create download landing page

**Licensing**:
- [ ] Implement license key system
- [ ] Add subscription management
- [ ] Create trial/demo mode
- [ ] Build license validation

---

## Technical Challenges & Solutions

### Challenge 1: Screen Capture Exclusion
**Problem**: Making window invisible to screen sharing
**Solution**: 
- macOS: Use `NSWindow.sharingType = .none`
- Windows: Use `SetWindowDisplayAffinity(WDA_EXCLUDEFROMCAPTURE)`
- Requires JNI/JNA for native calls from Kotlin

### Challenge 2: Global Hotkeys Without Focus Stealing
**Problem**: Responding to hotkeys while keeping browser focused
**Solution**:
- Register global hotkeys at OS level
- Use background event handlers
- Update window without requesting focus

### Challenge 3: Process Hiding
**Problem**: Hiding from Task Manager/Activity Monitor
**Solution**:
- macOS: Use `LSUIElement` or `LSBackgroundOnly` in Info.plist
- Windows: Process name obfuscation and DLL injection techniques
- Ethical consideration: Be transparent about this in terms of service

### Challenge 4: Cross-Platform Native APIs
**Problem**: Accessing OS-specific features from Kotlin
**Solution**:
- Use JNA (Java Native Access) for native calls
- Create platform-specific implementations
- Abstract behind common interface

### Challenge 5: AI Response Latency
**Problem**: Slow AI responses during time-sensitive interviews
**Solution**:
- Pre-cache common problems
- Use streaming responses
- Implement local LLM fallback
- Optimize prompt engineering

---

## Data Requirements

### Solution Database Content
1. **LeetCode Problems** (~3000 problems)
   - All difficulty levels
   - Multiple language solutions
   - Explanations and hints

2. **HackerRank Problems** (~1000 problems)
3. **CodeSignal Problems** (~500 problems)
4. **Platform-Agnostic Algorithms**
   - Common patterns (sliding window, two pointers, etc.)
   - Data structures implementations
   - System design templates

### Data Sources
- Web scraping (with legal considerations)
- Community contributions
- AI-generated solutions
- Manual curation

---

## Security & Privacy Considerations

### User Privacy
- No telemetry without explicit consent
- Local-first data storage
- Encrypted credentials
- No logging of interview sessions

### Ethical Considerations
- Clear terms of service about intended use
- Warning about potential policy violations
- Educational use disclaimer
- Responsibility lies with user

### Legal Considerations
- Review terms of service of interview platforms
- Consult legal counsel
- Implement proper disclaimers
- Consider jurisdiction-specific laws

---

## Monetization Strategy

### Pricing Tiers
1. **Free Tier**
   - Limited problem database (100 problems)
   - Basic features only
   - No audio support

2. **Pro Monthly** ($49-79/month)
   - Full problem database
   - All features including audio
   - Priority support
   - Regular updates

3. **Pro Lifetime** ($299-499 one-time)
   - All Pro features
   - Lifetime updates
   - Priority support

### Additional Revenue
- Affiliate program (40% commission as per original product)
- Enterprise/coaching partnerships
- Premium problem packs

---

## Testing Strategy

### Functional Testing
- [ ] Unit tests for core logic
- [ ] Integration tests for AI services
- [ ] Database tests
- [ ] UI tests with Compose testing framework

### Platform Testing
- [ ] Test on macOS (multiple versions)
- [ ] Test on Windows 10/11
- [ ] Test on Linux (optional)
- [ ] Multi-monitor setups
- [ ] Different screen resolutions

### Detection Testing
- [ ] Test with Zoom screen sharing
- [ ] Test with Google Meet
- [ ] Test with Microsoft Teams
- [ ] Test with Discord
- [ ] Test with OBS recording
- [ ] Test with various screenshot tools
- [ ] Test focus detection on interview platforms

### Performance Testing
- [ ] Startup time benchmarks
- [ ] Memory usage monitoring
- [ ] CPU usage during operation
- [ ] Network latency tests
- [ ] Database query performance

### User Acceptance Testing
- [ ] Beta testing with real users
- [ ] Usability testing
- [ ] Accessibility testing
- [ ] Feedback collection and iteration

---

## Development Timeline

### Month 1-2: Foundation
- Week 1-3: Native window management and stealth features
- Week 4-7: Core functionality (database, AI, basic UI)

### Month 3: Advanced Features
- Week 8-11: Audio support, positioning, context detection

### Month 4: Security & Polish
- Week 12-14: Anti-detection measures, security audit
- Week 15-17: UX improvements, performance optimization

### Month 5: Launch Preparation
- Week 18-20: Platform support, distribution, final testing
- Week 21-22: Beta launch and iteration

**Total Estimated Time**: 5-6 months with 1-2 developers

---

## Technology Stack (FINAL DECISIONS)

### Core Application
- **Language**: Kotlin 2.0+
- **Architecture**: KMM with Compose Multiplatform Desktop
- **UI Framework**: Compose Multiplatform 1.6+
- **Build System**: Gradle 8.5+ with Kotlin DSL
- **Native Interop**: JNA 5.14+ (Java Native Access)
- **Dependency Injection**: Koin 3.5+ (lightweight, KMM-compatible)

### Database Layer
- **Primary DB**: SQLDelight 2.0+ (KMM-native, type-safe SQL)
- **Driver**: SQLite JDBC for desktop
- **Caching**: Kotlin coroutines + in-memory Map with LRU eviction
- **Migrations**: SQLDelight migration support

### AI Integration
- **Primary Provider**: OpenAI GPT-4 API (best code generation)
- **Fallback Provider**: Anthropic Claude 3.5 Sonnet
- **Local LLM**: Ollama integration (optional, privacy mode)
- **HTTP Client**: Ktor Client 2.3+ (KMM-compatible, coroutines)
- **JSON**: Kotlinx.serialization 1.6+
- **Streaming**: Server-Sent Events (SSE) via Ktor

### Audio System
- **macOS TTS**: JNA bindings to AVFoundation/AVSpeechSynthesizer
- **Windows TTS**: JNA bindings to SAPI (ISpVoice)
- **Cloud TTS Fallback**: ElevenLabs API (high quality)
- **Audio Playback**: Java Sound API (javax.sound.sampled)
- **Audio Format**: MP3/WAV via JLayer or similar

### Native Platform Integration
- **JNA Version**: 5.14.0
- **macOS APIs**: 
  - Cocoa (NSWindow, NSApplication)
  - Carbon (RegisterEventHotKey)
  - Core Graphics (window positioning)
- **Windows APIs**:
  - User32.dll (SetWindowDisplayAffinity, RegisterHotKey)
  - Kernel32.dll (process manipulation)
  - Dwmapi.dll (DWM effects)

### UI & Utilities
- **Syntax Highlighting**: Compose-based custom implementation with Regex
- **Code Formatting**: Custom formatter or KtLint integration
- **Markdown Rendering**: Compose-based Markdown parser
- **Logging**: Kotlin Logging + SLF4J + Logback
- **Testing**: 
  - JUnit 5 (unit tests)
  - Kotest (assertions)
  - MockK (mocking)
  - Compose UI Testing
  - Turbine (Flow testing)

### Distribution & Updates
- **Packaging**: 
  - macOS: DMG via jpackage + create-dmg
  - Windows: MSI via jpackage + WiX Toolset
  - Linux: AppImage via jpackage
- **Code Signing**:
  - macOS: Apple Developer certificate + notarization
  - Windows: DigiCert/Sectigo code signing certificate
- **Auto-Updates**: Custom implementation using:
  - GitHub Releases API for version checking
  - Ktor for download
  - Platform-specific installers
- **Crash Reporting**: Sentry SDK (optional, opt-in)
- **Analytics**: None (privacy-first approach)

### Development Tools
- **IDE**: IntelliJ IDEA 2024.1+
- **Kotlin Version**: 2.0.0+
- **JDK**: JDK 17+ (LTS)
- **Version Control**: Git
- **CI/CD**: GitHub Actions
- **Code Quality**: Detekt + ktlint

---

## Risk Assessment

### High Risk
1. **Detection by Interview Platforms**: Continuous cat-and-mouse game
   - Mitigation: Regular testing, community feedback, rapid updates

2. **Legal Issues**: Potential ToS violations
   - Mitigation: Legal review, clear disclaimers, user responsibility

3. **Platform API Changes**: OS updates breaking stealth features
   - Mitigation: Monitor OS updates, maintain compatibility layers

### Medium Risk
1. **AI API Costs**: High usage costs with cloud AI
   - Mitigation: Caching, local LLM fallback, usage limits

2. **Competition**: Similar products entering market
   - Mitigation: Focus on quality, unique features, community

3. **User Acquisition**: Reaching target audience
   - Mitigation: SEO, content marketing, affiliate program

### Low Risk
1. **Technical Complexity**: Challenging but achievable
   - Mitigation: Experienced team, iterative development

2. **Performance Issues**: Manageable with optimization
   - Mitigation: Profiling, optimization, testing

---

## Success Metrics

### Technical Metrics
- Startup time < 2 seconds
- Memory usage < 200MB
- 99.9% screen share invisibility
- Zero focus stealing incidents
- AI response time < 3 seconds

### Business Metrics
- 1000+ active users in first 3 months
- 20%+ conversion rate (free to paid)
- 4.5+ star rating
- < 5% churn rate
- 40%+ affiliate conversion

### User Satisfaction
- Net Promoter Score (NPS) > 50
- 90%+ report successful interview assistance
- < 1% detection rate reported
- Positive community feedback

---

## Next Steps

### Immediate Actions (Week 1)
1. Set up development environment
2. Create proof-of-concept for window hiding
3. Test screen capture exclusion on target platforms
4. Design database schema
5. Create project structure and architecture

### Short-term Goals (Month 1)
1. Complete Phase 1 (Foundation)
2. Begin Phase 2 (Core Functionality)
3. Create MVP with basic features
4. Start building problem database

### Long-term Goals (Months 2-5)
1. Complete all phases
2. Conduct thorough testing
3. Launch beta program
4. Iterate based on feedback
5. Public launch

---

## Conclusion

This implementation plan provides a comprehensive roadmap for building an interview assistant application similar to Interview Coder. The key technical challenges involve native OS integration for stealth features, AI integration for solution generation, and creating an intuitive user experience.

The project is technically feasible with Kotlin Multiplatform and Compose Desktop, leveraging the existing project structure. Success depends on careful implementation of anti-detection measures, quality of the solution database, and user experience design.

**Estimated Effort**: 5-6 months with 1-2 experienced developers
**Estimated Budget**: $50K-100K (development + infrastructure + legal)
**Market Opportunity**: Growing demand for interview preparation tools

---

## Appendix

### A. Native API References
- macOS Window Management: [NSWindow Documentation](https://developer.apple.com/documentation/appkit/nswindow)
- Windows Display Affinity: [SetWindowDisplayAffinity](https://docs.microsoft.com/en-us/windows/win32/api/winuser/nf-winuser-setwindowdisplayaffinity)
- JNA Documentation: [Java Native Access](https://github.com/java-native-access/jna)

### B. Similar Products Analysis
- Interview Coder (reference product)
- Final Round AI
- AIApply
- UltraCode AI
- LockedIn AI

### C. Legal Resources
- Terms of Service templates
- Privacy policy templates
- Software licensing guides
- Ethical considerations in AI-assisted interviews

### D. Development Resources
- Compose Desktop documentation
- Kotlin Multiplatform guides
- JNA tutorials
- AI API documentation (OpenAI, Anthropic)

---

---

## Implementation Checklist

### Phase 1: Foundation ✓ Ready to Implement
- [ ] Set up KMM project structure
- [ ] Add all dependencies to build.gradle.kts
- [ ] Create expect/actual classes for WindowManager
- [ ] Implement macOS native bindings (NSWindow, Carbon)
- [ ] Implement Windows native bindings (User32, Kernel32)
- [ ] Create HotkeyManager with global hotkey registration
- [ ] Configure process hiding (Info.plist, taskbar)
- [ ] Test stealth features on both platforms

### Phase 2: Core Functionality ✓ Ready to Implement
- [ ] Create SQLDelight database schema
- [ ] Implement ProblemRepository and SolutionRepository
- [ ] Set up Ktor client with OpenAI integration
- [ ] Create AIService interface and implementations
- [ ] Build Compose UI (SearchScreen, SolutionScreen, SettingsScreen)
- [ ] Implement ViewModels with StateFlow
- [ ] Set up Koin dependency injection
- [ ] Test end-to-end flow (search → solution → display)

### Phase 3: Advanced Features ✓ Ready to Implement
- [ ] Implement AudioService with TTS (macOS/Windows)
- [ ] Create ExplanationGenerator
- [ ] Add audio UI controls
- [ ] Implement PositioningService
- [ ] Create ClipboardMonitor
- [ ] Build ProblemMatcher
- [ ] Add ReasoningService
- [ ] Test all advanced features

### Phase 4: Security & Testing
- [ ] Conduct security audit
- [ ] Test screen capture exclusion (Zoom, Meet, Teams)
- [ ] Verify process hiding
- [ ] Test focus preservation
- [ ] Perform penetration testing
- [ ] Document security measures

### Phase 5: Polish & Distribution
- [ ] Performance optimization
- [ ] Create onboarding flow
- [ ] Build installers (DMG, MSI)
- [ ] Set up code signing
- [ ] Implement auto-update system
- [ ] Write documentation
- [ ] Beta testing
- [ ] Launch

---

## Quick Reference: Key Files to Create

### Common Main (Shared Code)
```
commonMain/kotlin/
├── data/
│   ├── repository/ProblemRepository.kt
│   └── repository/SolutionRepository.kt
├── domain/
│   ├── model/Problem.kt
│   ├── model/Solution.kt
│   └── usecase/GetSolutionUseCase.kt
├── ui/
│   ├── App.kt
│   ├── search/SearchScreen.kt
│   ├── solution/SolutionScreen.kt
│   ├── settings/SettingsScreen.kt
│   └── viewmodel/MainViewModel.kt
├── ai/
│   ├── AIService.kt
│   ├── OpenAIService.kt
│   └── ClaudeService.kt
├── audio/
│   ├── AudioService.kt
│   └── ExplanationGenerator.kt
├── platform/
│   ├── WindowManager.kt (expect)
│   └── HotkeyManager.kt (expect)
└── di/AppModule.kt

commonMain/sqldelight/
└── com/github/ericomonteiro/copilot/db/Database.sq
```

### macOS Main
```
macosMain/kotlin/
├── platform/
│   ├── WindowManager.kt (actual)
│   ├── HotkeyManager.kt (actual)
│   └── ProcessManager.kt
└── audio/
    └── MacOSAudioService.kt (actual)
```

### Windows Main
```
windowsMain/kotlin/
├── platform/
│   ├── WindowManager.kt (actual)
│   ├── HotkeyManager.kt (actual)
│   └── ProcessManager.kt
└── audio/
    └── WindowsAudioService.kt (actual)
```

---

**Document Version**: 2.0 - KMM Implementation Ready  
**Last Updated**: November 2024  
**Author**: AI Assistant  
**Status**: Ready for Implementation

**Next Action**: Begin Phase 1 - Set up project structure and native window management
