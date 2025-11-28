# Stealth Mode

## Overview

Stealth Mode hides the Interview Assistant window from screen capture and recording software like Zoom, Google Meet, Microsoft Teams, and macOS screenshot tools.

## ✅ Status: Fully Implemented

The stealth mode feature is **fully functional** on macOS using a native JNI library.

## How It Works

### macOS Implementation

- Uses native Objective-C code via JNI (Java Native Interface)
- Calls `NSWindow.setSharingType()` to hide windows from screen capture
- Runs in the same process as the JVM, allowing direct window manipulation
- Compiled as a universal binary (arm64 + x86_64)

### Technical Details

When stealth mode is enabled:
- `NSWindowSharingType` is set to `NSWindowSharingNone` (0)
- Window becomes invisible to screen recording and screenshot tools
- Window remains visible on your screen
- Other participants in video calls cannot see the window

When stealth mode is disabled:
- `NSWindowSharingType` is set to `NSWindowSharingReadOnly` (1)
- Window appears normally in screen captures

## Usage

### Enable/Disable Stealth Mode

1. Open the application
2. Click Settings (⚙️ icon)
3. Toggle "Hide from Screen Capture"
4. The setting is saved automatically

### Testing

#### Test with Screenshot (macOS)

```bash
# Take a screenshot
screencapture -x ~/Desktop/test_stealth.png

# Or use keyboard shortcut
# Cmd + Shift + 4
```

**Expected Result:**
- **Stealth Mode ON**: Window appears transparent/empty in screenshot
- **Stealth Mode OFF**: Window appears normally in screenshot

#### Test with Screen Recording

**QuickTime Player:**
1. Open QuickTime Player
2. File → New Screen Recording
3. Start recording
4. The Interview Assistant window should be invisible in the recording

**Zoom/Meet/Teams:**
1. Start a meeting
2. Share your screen
3. The Interview Assistant window should be invisible to other participants
4. You can still see it on your screen

## Building from Source

The native library is pre-compiled and included in the resources. If you need to rebuild:

```bash
# Navigate to native directory
cd native/macos

# Run build script
./build_jni.sh
```

**Requirements:**
- macOS 10.13+
- Xcode Command Line Tools
- JDK 17+

The build script will:
1. Compile `stealth_jni.m` to `libstealth.dylib`
2. Create a universal binary (arm64 + x86_64)
3. Copy to `composeApp/src/jvmMain/resources/native/macos/`

## Architecture

### Files

```
native/macos/
├── stealth_jni.m          # JNI implementation in Objective-C
├── build_jni.sh           # Build script
└── stealth_mode.m         # Standalone utility (deprecated)

composeApp/src/jvmMain/
├── resources/native/macos/
│   └── libstealth.dylib   # Compiled native library
└── kotlin/.../platform/
    └── WindowManager.kt   # Kotlin integration
```

### Code Flow

```
User toggles Stealth Mode
    ↓
SettingsViewModel.setHideFromCapture(true)
    ↓
WindowManager.setHideFromCapture(true)
    ↓
WindowManager.nativeSetHideFromCapture(true)  [JNI call]
    ↓
stealth_jni.m: Java_..._nativeSetHideFromCapture()
    ↓
NSApplication.sharedApplication().windows
    ↓
window.setSharingType(NSWindowSharingNone)
    ↓
Window hidden from screen capture ✅
```

## Platform Support

| Platform | Status | Implementation |
|----------|--------|----------------|
| macOS 10.13+ | ✅ Fully Supported | JNI + NSWindow API |
| Windows 10 2004+ | ⚠️ Planned | SetWindowDisplayAffinity |
| Linux | ❌ Not Supported | TBD |

## Troubleshooting

### Native Library Not Loading

**Symptom:**
```
WindowManager: Failed to load native library
WindowManager: Stealth mode will not be available
```

**Solution:**
1. Verify the library exists:
   ```bash
   ls -la composeApp/src/jvmMain/resources/native/macos/libstealth.dylib
   ```

2. Rebuild the library:
   ```bash
   cd native/macos && ./build_jni.sh
   ```

3. Clean and rebuild the project:
   ```bash
   ./gradlew clean
   ./gradlew :composeApp:run
   ```

### Stealth Mode Not Working

**Symptom:** Window still appears in screenshots

**Checklist:**
1. ✅ Check console for success message:
   ```
   [Stealth] Mode ENABLED - Updated 1 window(s)
   ```

2. ✅ Verify macOS version:
   ```bash
   sw_vers
   ```
   Requires macOS 10.13 (High Sierra) or later

3. ✅ Test with native screenshot tool (not third-party apps)

4. ✅ Restart the application after toggling

### Permission Issues

Some screen recording apps may require additional permissions. Grant screen recording permissions in:

**System Settings → Privacy & Security → Screen Recording**

## Limitations

1. **macOS Only**: Currently only implemented for macOS
2. **Native Dependency**: Requires compiled native library
3. **System Screenshots**: Works with macOS native tools (screencapture, Cmd+Shift+4)
4. **Third-Party Apps**: Some apps using private APIs may still capture the window
5. **Physical Recording**: Cannot prevent physical camera recordings of your screen

## Security Considerations

- Stealth mode is designed for privacy during legitimate screen sharing
- Does not prevent all forms of screen capture
- Use ethically and in accordance with interview guidelines
- Some organizations may have policies against using such tools

## Performance

- **Minimal Impact**: Native code runs only when toggling the setting
- **No Polling**: No continuous background checks
- **Efficient**: Direct system calls via JNI
- **Memory**: ~50KB for native library

## Future Enhancements

- [ ] Windows support using `SetWindowDisplayAffinity`
- [ ] Linux support (X11/Wayland)
- [ ] Visual indicator in UI showing stealth status
- [ ] Hotkey to quickly toggle stealth mode
- [ ] Per-window stealth control

## Developer Notes

### JNI Method Signature

The native method is declared in `WindowManager.kt`:

```kotlin
private external fun nativeSetHideFromCapture(hide: Boolean)
```

And implemented in `stealth_jni.m`:

```c
JNIEXPORT void JNICALL 
Java_com_github_ericomonteiro_copilot_platform_WindowManager_nativeSetHideFromCapture
  (JNIEnv *env, jobject obj, jboolean hide)
```

### Library Loading

The library is extracted from resources to a temp file and loaded at runtime:

```kotlin
private fun loadNativeLibrary() {
    val resourcePath = "/native/macos/libstealth.dylib"
    val stream = this::class.java.getResourceAsStream(resourcePath)
    val tempFile = File.createTempFile("libstealth", ".dylib")
    tempFile.deleteOnExit()
    stream.use { input ->
        tempFile.outputStream().use { output ->
            input.copyTo(output)
        }
    }
    System.load(tempFile.absolutePath)
}
```

## References

- [NSWindow Documentation](https://developer.apple.com/documentation/appkit/nswindow)
- [NSWindowSharingType](https://developer.apple.com/documentation/appkit/nswindowsharingtype)
- [JNI Specification](https://docs.oracle.com/javase/8/docs/technotes/guides/jni/)

## License

This feature is part of the Interview Assistant project and follows the same license.

---

**Status**: ✅ Production Ready  
**Version**: 1.0.0  
**Last Updated**: November 2025
