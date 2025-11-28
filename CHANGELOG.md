# Changelog

All notable changes to the Interview Assistant MVP project.

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

### Version 1.1 (Planned)
- [ ] Global hotkeys (Ctrl/Cmd+B to toggle visibility)
- [ ] Advanced screen capture hiding
- [ ] Process hiding from task manager
- [ ] More problems (100+)
- [ ] Additional programming languages (C++, Go, Rust)
- [ ] Custom problem addition

### Version 1.2 (Planned)
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
- 3 deprecation warnings in Compose APIs (non-blocking)
- Window decorations visible (stealth features not yet implemented)

### Workarounds
- **Hotkeys**: Use Alt+Tab or Cmd+Tab to switch windows
- **Stealth**: Window is set to always-on-top
- **Hiding**: Minimize window manually

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

**Current Version**: 1.0.1
**Status**: Stable
**Last Updated**: November 28, 2024
