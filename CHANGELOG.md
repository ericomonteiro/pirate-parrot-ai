# Changelog

All notable changes to the Interview Assistant project.

---

## [1.2.0] - 2025-11-28

### Added
- **Stealth Mode (macOS)**: Hide window from screen capture and recording ✅
  - Native JNI implementation using Objective-C
  - Works with Zoom, Google Meet, Microsoft Teams, and macOS screenshots
  - Toggle in Settings → "Hide from Screen Capture"
  - **Keyboard Shortcut**: Cmd+B (macOS) or Ctrl+B (Windows) to toggle instantly
  - Universal binary (arm64 + x86_64)
  - Runs in same process as JVM for reliable window access
  - **Persistent Settings**: Stealth mode state loaded automatically on app startup

### New Components
- `stealth_jni.m` - Native JNI implementation in Objective-C
- `libstealth.dylib` - Compiled native library
- `build_jni.sh` - Build script for native library
- Native method `nativeSetHideFromCapture()` in WindowManager

### Technical
- JNI integration for native macOS APIs
- NSWindow.setSharingType() for screen capture control
- Automatic library extraction and loading at runtime
- Thread-safe implementation using dispatch_async
- Comprehensive error handling and logging
- **Settings Persistence**: Initial stealth mode and opacity loaded from database on startup
- **Retry Logic**: AWT window detection with automatic retry (up to 10 attempts)

### Documentation
- Added `STEALTH_MODE.md` with complete implementation guide
- Consolidated and removed redundant documentation files
- Updated README with stealth mode status
- All documentation in English

---

## [1.1.0] - 2024-11-28

### Added
- **Screenshot Analysis Feature**: Capture coding challenges from screen and get AI solutions
  - Click camera icon to capture entire screen
  - Automatic analysis with Gemini Vision API
  - Support for 7 programming languages
  - Real-time solution generation with explanation
  - Complexity analysis (time/space)
  
### New Components
- `ScreenshotService` - Platform-specific screen capture
- `ScreenshotAnalysisScreen` - UI for displaying analysis results
- `ScreenshotAnalysisViewModel` - State management for screenshot analysis
- `analyzeCodingChallenge()` method in AIService

### Technical
- Implemented expect/actual pattern for multiplatform screenshot capture
- Java AWT Robot for JVM screenshot capture
- Base64 image encoding for API submission
- Gemini Vision API integration with inline image data

### Documentation
- Added `SCREENSHOT_FEATURE.md` with complete feature documentation
- Updated README with new features
- All documentation now in English

---

## [1.0.2] - 2024-11-28

### Changed
- **AI Model**: Switched from `gpt-4-turbo-preview` to `gpt-3.5-turbo`
  - ✅ **Free tier compatible** - works with OpenAI free accounts
  - ✅ **Faster** - 3-5 seconds vs 10-15 seconds
  - ✅ **Cheaper** - $0.002 vs $0.03 per solution
  - ✅ **Good quality** - works great for most interview problems
  - Allows ~2,500 solutions with $5 free credits

### Added
- **FREE_TIER_GUIDE.md**: Comprehensive guide for free tier users
  - How to get started with free credits
  - Cost breakdown and monitoring
  - Tips to maximize credits
  - Model comparison
  - Troubleshooting

---

## [1.0.1] - 2024-11-28

### Added
- **Copy Error Button**: Added "Copy Error" button next to "Retry" button in error state
  - Allows users to easily copy error messages to clipboard
  - Useful for troubleshooting and reporting issues
  - Uses system clipboard for cross-platform compatibility

### Improved
- **Better Error Messages**: Enhanced error handling in OpenAI API service
  - API key validation before making requests
  - HTTP status code checking with detailed error messages
  - Clear error messages: "API key not configured", "Invalid authentication", etc.
  - Empty response validation

### Fixed
- **kotlinx-datetime Dependency**: Replaced `Clock.System` with `System.currentTimeMillis()`
  - Eliminates runtime ClassNotFoundException
  - Reduces dependencies
  - Improves startup reliability

### Documentation
- Added **TROUBLESHOOTING.md** with comprehensive error solutions
- Added **RUNNING.md** with current status and usage guide
- Added **BUILD_SUCCESS.md** with build information
- Added **CHANGELOG.md** (this file)

---

## [1.0.0] - 2024-11-28

### Initial MVP Release

#### Core Features
- **Search Functionality**: Real-time problem search with 30 pre-loaded problems
- **AI Solution Generation**: GPT-4 powered code generation in 4 languages
- **Solution Caching**: Instant access to previously generated solutions
- **Settings Management**: API key and preferences persistence
- **Multi-Language Support**: Kotlin, Java, Python, JavaScript

#### User Interface
- **Search Screen**: Problem list with search and filtering
- **Solution Screen**: Code display with tabs (Code/Explanation)
- **Settings Screen**: API key configuration and opacity control
- **Dark Theme**: Optimized for coding interviews

#### Technical Implementation
- **Kotlin Multiplatform**: Cross-platform desktop application
- **Compose Multiplatform**: Modern declarative UI
- **SQLDelight**: Type-safe database with caching
- **Ktor Client**: HTTP client for OpenAI API
- **Koin**: Dependency injection
- **JNA**: Native platform integration (prepared for future features)

#### Database
- 30 pre-loaded coding problems
- Automatic seeding on first run
- Solution caching with SQLite
- Settings persistence

#### Documentation
- **HOWTO.md**: Complete user guide (2000+ lines)
- **README.md**: Project overview and setup
- **DEVELOPER_GUIDE.md**: Implementation guide
- **QUICKSTART.md**: Fast setup guide
- **IMPLEMENTATION_STATUS.md**: Progress tracking
- **SUMMARY.md**: Project overview
- **INDEX.md**: Documentation hub

---

## Roadmap

### Version 1.3 (Planned)
- [ ] Global hotkeys (Ctrl/Cmd+B to toggle visibility)
- [ ] Stealth mode for Windows (SetWindowDisplayAffinity)
- [ ] Process hiding from task manager
- [ ] More problems (100+)
- [ ] Additional programming languages (C++, Go, Rust)
- [ ] Custom problem addition

### Version 1.4 (Planned)
- [ ] Audio/TTS explanations
- [ ] Context detection (clipboard monitoring)
- [ ] Auto-positioning over code editors
- [ ] Multiple AI providers (Claude, local LLM)
- [ ] Interview tips and hints

### Version 2.0 (Future)
- [ ] Auto-updates
- [ ] Analytics (opt-in)
- [ ] Community problem database
- [ ] Practice mode
- [ ] Mock interview simulator

---

## Breaking Changes

None yet (MVP release)

---

## Known Issues

### Non-Critical
- SLF4J warning on startup (cosmetic, no impact)
- Compose API deprecation warnings (non-blocking)
- Window opacity control disabled (requires undecorated window)

### Platform Limitations
- **Stealth Mode**: macOS only (Windows support planned for v1.3)
- **Global Hotkeys**: Not yet implemented (planned for v1.3)

---

## Migration Guide

### From 1.0.0 to 1.0.1

No migration needed. Changes are backward compatible.

**Database**: No schema changes
**Settings**: Fully compatible
**API**: No breaking changes

Simply update and run:
```bash
git pull
./gradlew :composeApp:run
```

---

## Contributors

- AI Assistant (Implementation)
- User (Requirements and Testing)

---

## License

See LICENSE file for details.

---

## Support

- **Documentation**: See [INDEX.md](./INDEX.md)
- **Troubleshooting**: See [TROUBLESHOOTING.md](./TROUBLESHOOTING.md)
- **Issues**: Create a GitHub issue

---

**Current Version**: 1.2.0
**Status**: Stable
**Last Updated**: November 28, 2025
