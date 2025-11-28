# Feature: Coding Challenge Analysis via Screenshot

## Description
This feature allows you to capture screenshots of coding challenges from your screen and send them to AI (Gemini) for analysis and solution generation.

## Implemented Components

### 1. Screenshot Capture Service
- **Location**: `composeApp/src/jvmMain/kotlin/com/github/ericomonteiro/copilot/screenshot/`
- **Files**:
  - `ScreenshotService.kt` - Interface and implementation for screen capture
  - `ScreenshotCapture.kt` - Expect/actual function for multiplatform
  - `ScreenshotCapture.jvm.kt` - JVM implementation using `java.awt.Robot`

### 2. Image Analysis with AI
- **Location**: `composeApp/src/commonMain/kotlin/com/github/ericomonteiro/copilot/ai/AIService.kt`
- **Method**: `analyzeCodingChallenge(imageBase64: String, language: String)`
- **Support**:
  - ✅ Gemini (gemini-2.5-flash) - Supports vision
  - ❌ OpenAI - Requires GPT-4 Vision (not available in free tier)

### 3. User Interface
- **Analysis Screen**: `ScreenshotAnalysisScreen.kt`
  - Shows loading during analysis
  - Displays generated code in tabs (Code/Explanation)
  - Allows programming language selection
  - Error handling with retry option

- **ViewModel**: `ScreenshotAnalysisViewModel.kt`
  - Gerencia estado da análise
  - Integração com AIService

### 4. App Integration
- Capture button in `SearchScreen` (camera icon)
- Automatic navigation to analysis screen after capture
- Image conversion to Base64 for API submission

## How to Use

1. **Via Button**:
   - Click the camera icon in the top right corner of the main screen
   - Screenshot will be captured automatically
   - Analysis will start and solution will be displayed

2. **Requirements**:
   - Gemini API Key configured in Settings
   - Gemini model selected (default: gemini-2.5-flash)

## Data Flow

```
[User clicks capture] 
    → [captureScreenshot() - Robot captures screen]
    → [Convert to Base64]
    → [Navigate to ScreenshotAnalysisScreen]
    → [AIService.analyzeCodingChallenge()]
    → [Gemini API with vision]
    → [JSON response with code and explanation]
    → [Display in UI]
```

## AI Response Format

```json
{
  "code": "complete solution code",
  "explanation": "approach explanation",
  "timeComplexity": "O(...)",
  "spaceComplexity": "O(...)"
}
```

## File Structure

```
composeApp/src/
├── commonMain/kotlin/com/github/ericomonteiro/copilot/
│   ├── ai/
│   │   └── AIService.kt (+ analyzeCodingChallenge)
│   ├── screenshot/
│   │   └── ScreenshotCapture.kt (expect)
│   ├── ui/
│   │   ├── App.kt (navegação)
│   │   ├── search/SearchScreen.kt (botão de captura)
│   │   └── screenshot/
│   │       ├── ScreenshotAnalysisScreen.kt
│   │       └── ScreenshotAnalysisViewModel.kt
│   └── di/
│       └── AppModule.kt (DI setup)
└── jvmMain/kotlin/com/github/ericomonteiro/copilot/
    └── screenshot/
        ├── ScreenshotService.kt
        └── ScreenshotCapture.jvm.kt (actual)
```

## Known Limitations

1. **Screen Capture**: Captures entire screen (no region selection)
2. **OpenAI**: Does not support image analysis in free tier
3. **Quality**: Depends on image quality and text clarity in screenshot

## Future Improvements

- [ ] Region selection for capture
- [ ] Global keyboard shortcut (Cmd+Shift+S)
- [ ] Screenshot preview before sending
- [ ] Cache of previous analyses
- [ ] Support for multiple images
- [ ] Local OCR as fallback

## Troubleshooting

### Error: "Gemini API key is not configured"
- Go to Settings and configure your Gemini API key
- Get a free key at: https://makersuite.google.com/app/apikey

### Screenshot capture error
- Check accessibility permissions on macOS
- System Preferences → Security & Privacy → Privacy → Screen Recording

### Analysis returns error
- Check if the image contains readable text
- Try with a clearer screenshot
- Check your internet connection
