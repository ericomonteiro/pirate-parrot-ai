# Troubleshooting Guide

## Common Errors and Solutions

### Error: "Gemini API key is not configured"

**Full Error**:
```
Error: Gemini API key is not configured. Please set it in Settings.
```

**Cause**: No API key configured or invalid key

**Solution**:

#### 1. Get Free Gemini API Key
1. Visit https://makersuite.google.com/app/apikey
2. Sign in with Google account
3. Click "Create API Key"
4. Copy the key (starts with `AIza...`)

#### 2. Configure in App
1. Open Interview Assistant
2. Click Settings (⚙️)
3. Paste your Gemini API key
4. Click outside to save

#### 3. Verify It Works
1. Search for "Two Sum"
2. Click on problem
3. Solution should generate in 3-5 seconds

---

### Error: Screenshot Capture Failed

**Symptoms**: Camera icon clicked but nothing happens or error shown

**Possible Causes**:
1. Missing screen recording permissions (macOS)
2. Java AWT Robot not available
3. Display not accessible

**Solution**:

**macOS**:
1. Go to System Preferences → Security & Privacy
2. Click Privacy tab
3. Select "Screen Recording"
4. Add/enable the Java application
5. Restart the app

**Windows**:
- Usually works without special permissions
- Check antivirus isn't blocking screen capture

**Linux**:
- Ensure X11 or Wayland display is accessible
- Check display permissions

---

### Error: Screenshot Analysis Returns Error

**Symptoms**: Screenshot captured but analysis fails

**Possible Causes**:
1. Image quality too low
2. Text not readable in screenshot
3. Network error
4. Gemini API issue

**Solution**:
1. Try with a clearer screenshot
2. Ensure coding challenge text is visible and readable
3. Check internet connection
4. Verify Gemini API key is valid
5. Click "Retry" button

---

### Error: "Field 'choices' is required but it was missing"

**Full Error**:
```
Error: Illegal input: Field 'choices' is required for type with serial name 
'com.github.ericomonteiro.copilot.ai.OpenAIResponse', but it was missing at path: $
```

**Cause**: This error occurs when the OpenAI API returns an error response instead of a successful response. Common reasons:
1. Invalid or missing API key
2. API key has no credits
3. Rate limit exceeded
4. Network error

**Solution**:

#### 1. Verify API Key is Set

**In the Application**:
1. Click Settings (⚙️)
2. Check if API key is entered
3. Make sure it starts with `sk-`
4. Save the settings

**Via Environment Variable**:
```bash
export OPENAI_API_KEY="sk-your-actual-key-here"
./gradlew :composeApp:run
```

#### 2. Check API Key is Valid

Go to https://platform.openai.com/api-keys and:
1. Verify your key exists
2. Check it hasn't been revoked
3. Generate a new key if needed

#### 3. Verify Account Has Credits

Go to https://platform.openai.com/usage and:
1. Check your current balance
2. Add credits if balance is $0
3. Set up billing if needed

#### 4. Check for Better Error Messages

After the fix applied in this version, you should now see more helpful error messages:
- "OpenAI API key is not configured. Please set it in Settings."
- "OpenAI API error (401): Invalid authentication"
- "OpenAI API error (429): Rate limit exceeded"

---

### Error: "ClassNotFoundException: kotlinx.datetime.Clock$System"

**Solution**: Already fixed in the latest version. We replaced `kotlinx.datetime.Clock` with `System.currentTimeMillis()`.

If you still see this:
```bash
./gradlew clean build
./gradlew :composeApp:run
```

---

### Error: "Cannot find OpenAI API key"

**Cause**: API key not configured

**Solution**:

**Option 1 - Settings Screen**:
1. Launch the app
2. Click Settings (⚙️)
3. Enter your API key
4. Click outside the text field to save

**Option 2 - Environment Variable**:
```bash
export OPENAI_API_KEY="sk-your-key-here"
./gradlew :composeApp:run
```

**Option 3 - Check Database**:
The API key is stored in `~/.interviewassistant/database.db`. If corrupted, delete it:
```bash
rm ~/.interviewassistant/database.db
# Restart the app - it will recreate the database
```

---

### Error: "No problems found"

**Cause**: Database not seeded or corrupted

**Solution**:
```bash
# Delete the database
rm ~/.interviewassistant/database.db

# Restart the app
./gradlew :composeApp:run

# The app will automatically seed 30 problems
```

---

### Error: "Failed to generate solution"

**Possible Causes**:
1. No internet connection
2. OpenAI API is down
3. Invalid API key
4. Rate limit exceeded
5. Insufficient credits

**Solutions**:

**Check Internet**:
```bash
ping api.openai.com
```

**Check OpenAI Status**:
Visit https://status.openai.com

**Try Again**:
Click the "Retry" button in the app

**Check Logs**:
Look at the console output for detailed error messages

---

### Error: "Connection timeout"

**Cause**: Network issues or slow connection

**Solution**:
1. Check your internet connection
2. Try again (timeout is set to 30 seconds)
3. Check if you're behind a proxy/firewall

---

### Error: "Rate limit exceeded"

**Full Error**: `OpenAI API error (429): Rate limit exceeded`

**Cause**: Too many requests to OpenAI API

**Solution**:
1. Wait a few minutes
2. Check your OpenAI rate limits at https://platform.openai.com/account/limits
3. Upgrade your OpenAI plan if needed
4. Use cached solutions (they load instantly)

---

### Application Won't Start

**Symptoms**: Window doesn't appear

**Solutions**:

**Check Java Version**:
```bash
java -version
# Should be 17 or higher
```

**Clean Build**:
```bash
./gradlew clean
./gradlew :composeApp:run
```

**Check for Errors**:
```bash
./gradlew :composeApp:run --stacktrace
```

---

### Window Appears Then Crashes

**Check Console Output**:
Look for error messages in the terminal

**Common Causes**:
1. Database corruption
2. Missing dependencies
3. Configuration errors

**Solution**:
```bash
# Delete database
rm ~/.interviewassistant/database.db

# Clean build
./gradlew clean build

# Run again
./gradlew :composeApp:run
```

---

### Solutions Not Caching

**Symptoms**: Every solution takes 5-10 seconds to load

**Cause**: Database write issues

**Check**:
```bash
# Check if database exists
ls -la ~/.interviewassistant/database.db

# Check permissions
ls -la ~/.interviewassistant/
```

**Solution**:
```bash
# Ensure directory is writable
chmod 755 ~/.interviewassistant
chmod 644 ~/.interviewassistant/database.db
```

---

### Search Not Working

**Symptoms**: Typing in search bar shows no results

**Causes**:
1. No problems in database
2. Search query too specific

**Solutions**:
1. Try searching for "Two Sum" (should always work)
2. Clear search and see all problems
3. Reseed database (delete and restart)

---

### Settings Not Saving

**Symptoms**: API key disappears after restart

**Cause**: Database write issues

**Check Database**:
```bash
sqlite3 ~/.interviewassistant/database.db "SELECT * FROM AppSettings;"
```

**Solution**:
```bash
# Delete and recreate
rm ~/.interviewassistant/database.db
./gradlew :composeApp:run
```

---

## Debug Mode

### Enable Detailed Logging

Run with debug output:
```bash
./gradlew :composeApp:run --debug 2>&1 | tee debug.log
```

### Check Database Contents

```bash
# Open database
sqlite3 ~/.interviewassistant/database.db

# List tables
.tables

# Check problems
SELECT COUNT(*) FROM Problem;

# Check settings
SELECT * FROM AppSettings;

# Exit
.quit
```

---

## Getting Help

### Before Asking for Help

1. Check this troubleshooting guide
2. Look at console output for errors
3. Try clean build: `./gradlew clean build`
4. Check OpenAI API status
5. Verify API key is valid

### What to Include

When reporting issues, include:
1. Error message (full text)
2. Console output
3. Steps to reproduce
4. Operating system and version
5. Java version (`java -version`)
6. Gradle version (`./gradlew --version`)

---

## Quick Fixes

### Reset Everything
```bash
# Delete database
rm ~/.interviewassistant/database.db

# Clean build
./gradlew clean

# Rebuild and run
./gradlew :composeApp:run
```

### Verify Installation
```bash
# Check Java
java -version

# Check Gradle
./gradlew --version

# Test build
./gradlew build

# Run
./gradlew :composeApp:run
```

---

## Performance Issues

### Slow Startup

**Normal**: 2-3 seconds
**Slow**: > 5 seconds

**Solutions**:
1. Close other applications
2. Check available memory
3. Use SSD instead of HDD

### Slow Search

**Normal**: Instant (< 100ms)
**Slow**: > 1 second

**Solutions**:
1. Reseed database (indexes might be missing)
2. Reduce number of problems
3. Check disk I/O

### Slow Solution Generation

**Normal**: 5-10 seconds (first time)
**Normal**: < 100ms (cached)
**Slow**: > 30 seconds

**Solutions**:
1. Check internet speed
2. Try different problem
3. Check OpenAI API status

---

## Platform-Specific Issues

### macOS

**"App is damaged"**:
```bash
xattr -cr /Applications/InterviewAssistant.app
```

**Permission Denied**:
```bash
chmod +x ./gradlew
```

### Windows

**"Windows protected your PC"**:
Click "More info" → "Run anyway"

**PowerShell Execution Policy**:
```powershell
Set-ExecutionPolicy -ExecutionPolicy RemoteSigned -Scope CurrentUser
```

---

## Still Having Issues?

1. Check [HOWTO.md](./HOWTO.md) for detailed usage guide
2. Review [README.md](./README.md) for setup instructions
3. See [RUNNING.md](./RUNNING.md) for current status
4. Create an issue on GitHub with details

---

**Last Updated**: November 28, 2024
**Version**: 1.0 MVP
