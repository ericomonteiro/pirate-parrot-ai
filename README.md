# Interview Assistant MVP

AI-powered interview assistant that provides real-time coding solutions during technical interviews while remaining undetectable by screen sharing software.

## 🚀 Features

- **Undetectable**: Hidden from screen capture (Zoom, Meet, Teams)
- **AI-Powered**: GPT-4 generated solutions
- **Multi-Language**: Kotlin, Java, Python, JavaScript
- **Fast Search**: Instant problem lookup
- **Solution Caching**: Offline access to generated solutions
- **Stealth Mode**: Hidden from dock/taskbar

## 📋 Prerequisites

- JDK 17 or higher
- OpenAI API Key ([Get one here](https://platform.openai.com/api-keys))
- macOS 11+ or Windows 10+

## 🛠️ Development Setup

### 1. Clone the Repository

```bash
git clone <repository-url>
cd copilot
```

### 2. Set Environment Variable

```bash
# macOS/Linux
export OPENAI_API_KEY="sk-your-api-key-here"

# Windows (PowerShell)
$env:OPENAI_API_KEY="sk-your-api-key-here"
```

### 3. Build and Run

```bash
# macOS/Linux
./gradlew :composeApp:run

# Windows
.\gradlew.bat :composeApp:run
```

## 📦 Building Distributable Packages

### macOS DMG

```bash
./gradlew :composeApp:packageDmg
```

Output: `composeApp/build/compose/binaries/main/dmg/`

### Windows MSI

```bash
.\gradlew.bat :composeApp:packageMsi
```

Output: `composeApp\build\compose\binaries\main\msi\`

## 📖 User Documentation

See [HOWTO.md](./HOWTO.md) for complete user guide including:
- Installation instructions
- Setup guide
- Usage examples
- Troubleshooting
- FAQ

## 🏗️ Project Structure

```
copilot/
├── composeApp/
│   ├── src/
│   │   ├── commonMain/          # Shared code
│   │   │   ├── kotlin/
│   │   │   │   ├── ai/          # OpenAI integration
│   │   │   │   ├── data/        # Repository & models
│   │   │   │   ├── ui/          # Compose UI
│   │   │   │   └── di/          # Dependency injection
│   │   │   └── sqldelight/      # Database schema
│   │   └── jvmMain/             # Desktop-specific
│   └── build.gradle.kts
├── specs/                        # Implementation plans
├── HOWTO.md                      # User guide
└── README.md                     # This file
```

## 🔧 Technology Stack

- **Language**: Kotlin 2.2.20
- **UI**: Compose Multiplatform 1.9.1
- **Database**: SQLDelight 2.0.1
- **HTTP**: Ktor 2.3.7
- **DI**: Koin 3.5.3
- **AI**: OpenAI GPT-4 API

## 🧪 Testing

```bash
./gradlew test
```

## 📝 Implementation Status

### ✅ Completed (MVP)
- [x] Project setup and dependencies
- [x] Database schema (SQLDelight)
- [x] AI service (OpenAI integration)
- [x] Repository layer
- [x] User documentation

### 🚧 In Progress
- [ ] UI implementation (Compose)
- [ ] Platform-specific features (window management, hotkeys)
- [ ] Dependency injection setup
- [ ] Main entry point

### 📅 Planned
- [ ] Testing suite
- [ ] Performance optimization
- [ ] Packaging and distribution

## 🤝 Contributing

This is an MVP project. Contributions are welcome after the initial release.

## ⚠️ Disclaimer

This tool is for educational purposes only. Using it during actual interviews may violate platform terms of service. Use responsibly.

## 📄 License

See LICENSE file for details.

## 🔗 Links

- [Implementation Plan](./specs/implementation-plan.md)
- [MVP Plan](./specs/mvp-implementation-plan.md)
- [User Guide](./HOWTO.md)

---

**Status**: MVP Development
**Version**: 1.0.0-SNAPSHOT