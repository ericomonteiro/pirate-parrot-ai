# Quick Start Guide - Interview Assistant MVP

## For Users

### Installation (5 minutes)

1. **Get an OpenAI API Key**
   - Go to https://platform.openai.com/api-keys
   - Create a new API key
   - Copy it (starts with `sk-`)

2. **Download & Install**
   - **macOS**: Download `.dmg`, drag to Applications
   - **Windows**: Download `.msi`, run installer

3. **First Launch**
   - Open the app
   - Click Settings (⚙️)
   - Paste your API key
   - Click Save

4. **Test It**
   - Search for "Two Sum"
   - Click the problem
   - Select "Python"
   - View the solution!

### Usage (30 seconds)

1. **Search** → Type problem name
2. **Select** → Click problem card
3. **Choose Language** → Dropdown menu
4. **View Solution** → Code + Explanation tabs

**Hotkey**: `Ctrl+B` (Windows) / `Cmd+B` (macOS) to hide/show

---

## For Developers

### Setup (10 minutes)

```bash
# 1. Clone
git clone <repo-url>
cd copilot

# 2. Set API Key
export OPENAI_API_KEY="sk-your-key"

# 3. Run
./gradlew :composeApp:run
```

### Current Implementation Status

✅ **Done**:
- Dependencies configured
- Database schema created
- AI service implemented
- Repository layer ready
- Documentation complete

🚧 **TODO** (to complete MVP):
1. Create UI screens (Search, Solution, Settings)
2. Implement ViewModels
3. Add platform-specific code (window management)
4. Set up dependency injection
5. Create main entry point
6. Add sample data seeding

### Next Steps

The foundation is ready! To complete the MVP:

1. **Implement UI** (see `specs/mvp-implementation-plan.md` for code)
2. **Add platform features** (window hiding, hotkeys)
3. **Wire up DI** (Koin modules)
4. **Test end-to-end**
5. **Package for distribution**

### File Structure Created

```
✅ composeApp/src/commonMain/
   ├── sqldelight/com/github/ericomonteiro/copilot/db/
   │   └── Database.sq                    # Database schema
   └── kotlin/com/github/ericomonteiro/copilot/
       ├── ai/
       │   └── AIService.kt                # OpenAI integration
       └── data/repository/
           └── ProblemRepository.kt        # Data access

🚧 TODO:
   └── kotlin/com/github/ericomonteiro/copilot/
       ├── ui/                             # Compose screens
       ├── di/                             # Koin modules
       └── jvmMain/
           └── main.kt                     # Entry point
```

### Quick Commands

```bash
# Run app
./gradlew :composeApp:run

# Build DMG (macOS)
./gradlew :composeApp:packageDmg

# Build MSI (Windows)
./gradlew :composeApp:packageMsi

# Run tests
./gradlew test

# Clean build
./gradlew clean
```

---

## Troubleshooting

### "Cannot find OpenAI API key"
```bash
export OPENAI_API_KEY="sk-your-key-here"
```

### "Build failed"
- Check JDK version: `java -version` (need 17+)
- Run: `./gradlew clean build`

### "App won't start"
- Check logs in console
- Verify API key is set
- Try running from terminal to see errors

---

## Resources

- **Full Documentation**: [HOWTO.md](./HOWTO.md)
- **Implementation Plan**: [specs/mvp-implementation-plan.md](./specs/mvp-implementation-plan.md)
- **README**: [README.md](./README.md)

---

**Need Help?** Check the full documentation or create an issue on GitHub.
