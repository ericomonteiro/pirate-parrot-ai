# Interview Assistant MVP - User Guide

## Table of Contents
1. [Installation](#installation)
2. [Setup](#setup)
3. [Usage](#usage)
4. [Features](#features)
5. [Troubleshooting](#troubleshooting)
6. [FAQ](#faq)

---

## Installation

### Prerequisites
- **macOS 11+** or **Windows 10+**
- **Internet connection** (for AI solution generation)
- **OpenAI API Key** ([Get one here](https://platform.openai.com/api-keys))

### macOS Installation

1. **Download** the DMG file from the releases page
2. **Open** the DMG file
3. **Drag** Interview Assistant to your Applications folder
4. **Right-click** the app and select "Open" (first time only)
5. Click "Open" in the security dialog

### Windows Installation

1. **Download** the MSI installer from the releases page
2. **Run** the installer
3. Follow the installation wizard
4. Launch from Start Menu or Desktop shortcut

---

## Setup

### First Launch

1. **Launch the application**
   - macOS: Open from Applications folder
   - Windows: Click the desktop icon or search in Start Menu

2. **Configure API Key**
   - Click the **Settings** icon (⚙️) in the top-right corner
   - Enter your OpenAI API Key in the text field
   - The key is stored locally and never shared

3. **Adjust Settings** (Optional)
   - **Window Opacity**: Adjust transparency (50%-100%)
   - **Hide from Screen Capture**: Enable stealth mode (recommended)

4. **Click Save** or close settings

---

## Usage

### Basic Workflow

#### 1. Search for a Problem

1. In the search bar, type the problem name
   - Example: "Two Sum", "Reverse Linked List", "Binary Search"
2. Results appear instantly as you type
3. Problems are organized by:
   - **Platform**: LeetCode, HackerRank, etc.
   - **Difficulty**: Easy, Medium, Hard

#### 2. Select a Problem

1. Click on any problem card from the search results
2. The solution screen opens automatically

#### 3. Choose Programming Language

1. Click the language dropdown in the top-right
2. Select from:
   - Kotlin
   - Java
   - Python
   - JavaScript

#### 4. View Solution

The solution screen has two tabs:

**Code Tab**:
- Complete, working solution
- Syntax-highlighted code
- Copy-paste ready

**Explanation Tab**:
- Algorithm explanation
- Time complexity analysis
- Space complexity analysis
- Approach description

#### 5. Generate New Solution (if needed)

- If no cached solution exists, the app generates one automatically
- Generation takes 5-10 seconds
- Solutions are cached for future use

### Hotkeys

| Hotkey | Action |
|--------|--------|
| **Ctrl+B** (Windows) / **Cmd+B** (macOS) | Toggle window visibility |
| **Esc** | Go back to search |

### Stealth Features

#### Hide from Screen Sharing

When **"Hide from Screen Capture"** is enabled in settings:

✅ **Invisible on**:
- Zoom screen sharing
- Google Meet screen sharing
- Microsoft Teams screen sharing
- Discord screen sharing
- OBS recordings
- macOS screenshots (Cmd+Shift+4)
- Windows screenshots (Win+Shift+S)

❌ **Still visible**:
- Your physical screen (obviously!)
- Screen recording apps with special permissions

#### Hide from Task Manager

The app is configured to:
- Not appear in macOS Dock (when minimized)
- Not appear in Windows Taskbar (when minimized)
- Run silently in the background

**Note**: For development/testing, the app may still appear in Activity Monitor (macOS) or Task Manager (Windows) process lists.

---

## Features

### ✅ Included in MVP

1. **Undetectable Window**
   - Hidden from screen capture software
   - Transparent overlay option
   - Always-on-top mode

2. **Smart Search**
   - Instant search results
   - Filter by platform and difficulty
   - 50+ pre-loaded problems

3. **AI-Powered Solutions**
   - GPT-4 generated code
   - Multiple programming languages
   - Detailed explanations
   - Complexity analysis

4. **Solution Caching**
   - Previously generated solutions load instantly
   - No repeated API calls
   - Offline access to cached solutions

5. **Clean Interface**
   - Minimal, distraction-free design
   - Dark theme optimized for interviews
   - Easy navigation

### 🚧 Coming in Future Versions

- Audio explanations (TTS)
- Auto-detection of problems from clipboard
- Multiple AI providers
- Custom problem database
- Practice mode
- Interview tips and hints

---

## Troubleshooting

### App Won't Launch

**macOS**:
- **Problem**: "App is damaged and can't be opened"
- **Solution**: 
  ```bash
  xattr -cr /Applications/InterviewAssistant.app
  ```
  Then try opening again

**Windows**:
- **Problem**: "Windows protected your PC"
- **Solution**: Click "More info" → "Run anyway"

### API Key Issues

**Problem**: "Invalid API key" error

**Solutions**:
1. Verify your API key at https://platform.openai.com/api-keys
2. Make sure you copied the entire key (starts with `sk-`)
3. Check that your OpenAI account has credits
4. Try generating a new API key

**Problem**: "Rate limit exceeded"

**Solutions**:
1. Wait a few minutes and try again
2. Check your OpenAI usage limits
3. Upgrade your OpenAI plan if needed

### Solution Generation Fails

**Problem**: "Error generating solution"

**Solutions**:
1. Check your internet connection
2. Verify API key is correct
3. Try a different problem
4. Check OpenAI service status: https://status.openai.com

### Window Not Hiding from Screen Share

**macOS**:
1. Restart the application
2. Grant screen recording permissions:
   - System Settings → Privacy & Security → Screen Recording
   - Enable Interview Assistant

**Windows**:
1. Restart the application
2. Run as Administrator (right-click → Run as administrator)
3. Check Windows security settings

### Performance Issues

**Problem**: App is slow or laggy

**Solutions**:
1. Close other applications
2. Reduce window opacity to 100%
3. Clear cached solutions:
   - Delete `~/.interviewassistant/database.db` (macOS)
   - Delete `%APPDATA%\InterviewAssistant\database.db` (Windows)
4. Restart the application

---

## FAQ

### Is this detectable by interview platforms?

The app is designed to be undetectable by browser-based interview platforms because:
- It runs as a separate native application
- It's hidden from screen capture APIs
- It doesn't interact with the browser
- No browser extensions required

**However**: Always check the terms of service of the interview platform. Use responsibly.

### Does it work offline?

**Partially**:
- ✅ Cached solutions work offline
- ✅ Search works offline
- ❌ Generating new solutions requires internet (OpenAI API)

### How much does it cost?

**Application**: Free (MVP version)

**OpenAI API Costs**:
- Approximately $0.01-0.03 per solution
- GPT-4 Turbo pricing: https://openai.com/pricing
- Typical interview: $0.50-2.00 total

**Tip**: Pre-generate solutions for common problems to minimize costs during actual interviews.

### Can I add my own problems?

Not in the MVP version. This feature is planned for v1.1.

**Workaround**: You can manually add problems to the database:
```sql
INSERT INTO Problem (platform, problem_name, problem_number, difficulty, description, created_at)
VALUES ('Custom', 'My Problem', NULL, 'Medium', 'Problem description here', 1234567890000);
```

### What languages are supported?

**Code Generation**:
- Kotlin
- Java
- Python
- JavaScript

**More languages** can be added in future versions.

### Is my API key secure?

Yes:
- Stored locally on your computer
- Never transmitted except to OpenAI
- Not logged or shared
- Encrypted in storage (platform-dependent)

**Location**:
- macOS: `~/Library/Application Support/InterviewAssistant/`
- Windows: `%APPDATA%\InterviewAssistant\`

### Can I use this for practice?

Absolutely! The app is perfect for:
- Learning algorithms
- Understanding solutions
- Practicing explanations
- Studying complexity analysis

### Does it work with all interview platforms?

Tested and working with:
- ✅ LeetCode
- ✅ HackerRank
- ✅ CodeSignal
- ✅ Coderbyte
- ✅ CoderPad
- ✅ Generic coding environments

### What if I get caught?

**Disclaimer**: This tool is for educational purposes. Using it during actual interviews may violate platform terms of service. The developers are not responsible for any consequences of misuse.

**Recommendation**: Use for practice and learning, not for cheating.

---

## Tips for Best Results

### Before the Interview

1. **Pre-generate solutions** for common problems
2. **Practice explaining** solutions out loud
3. **Test the hotkey** (Ctrl/Cmd+B) multiple times
4. **Verify stealth mode** works with your screen sharing software
5. **Check API key** has sufficient credits

### During the Interview

1. **Position the window** over a non-critical area
2. **Use low opacity** (70-80%) to see through it
3. **Read the solution first**, then type it naturally
4. **Don't copy-paste** directly - type it out
5. **Explain your thought process** using the explanation tab
6. **Modify the solution** slightly to make it your own

### After the Interview

1. **Review the solutions** you used
2. **Understand the algorithms** deeply
3. **Practice similar problems** without the tool
4. **Learn from the experience**

---

## Keyboard Shortcuts Reference

| Shortcut | Action |
|----------|--------|
| `Ctrl+B` / `Cmd+B` | Toggle visibility |
| `Esc` | Go back |
| `Ctrl+F` / `Cmd+F` | Focus search bar |
| `Tab` | Navigate between tabs |
| `Ctrl+C` / `Cmd+C` | Copy code (when selected) |

---

## Support

### Getting Help

1. **Check this guide** first
2. **Search existing issues** on GitHub
3. **Create a new issue** with:
   - Your OS and version
   - Steps to reproduce
   - Error messages
   - Screenshots (if applicable)

### Reporting Bugs

Please include:
- Operating system and version
- Application version
- Steps to reproduce the bug
- Expected vs actual behavior
- Error messages or logs

### Feature Requests

We welcome feature requests! Please:
- Check if it's already requested
- Explain the use case
- Describe the expected behavior

---

## Privacy Policy

### Data Collection

**We DO NOT collect**:
- Personal information
- Usage statistics
- API keys
- Generated solutions
- Search queries

**Local Storage Only**:
- API key (encrypted)
- Cached solutions
- User preferences

### Third-Party Services

**OpenAI API**:
- Problem descriptions are sent to OpenAI
- Solutions are received from OpenAI
- Subject to OpenAI's privacy policy
- No personal data is included in requests

---

## License

This software is provided for educational purposes only. Use at your own risk.

---

## Credits

**Developed by**: Interview Assistant Team
**Powered by**: OpenAI GPT-4
**Built with**: Kotlin Multiplatform, Compose Desktop

---

## Version History

### v1.0.0 (MVP) - Current
- Initial release
- Basic search and solution display
- OpenAI integration
- Stealth features
- 4 programming languages

### Planned Updates
- v1.1: Audio explanations, context detection
- v1.2: Multiple AI providers, custom problems
- v2.0: Advanced features, community database

---

**Last Updated**: November 2024
**Document Version**: 1.0
