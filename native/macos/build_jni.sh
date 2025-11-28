#!/bin/bash

# Build script for Stealth Mode JNI library

set -e

SCRIPT_DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"
OUTPUT_DIR="$SCRIPT_DIR/../../composeApp/src/jvmMain/resources/native/macos"

echo "🔨 Building Stealth Mode JNI library..."
echo ""

# Find JAVA_HOME
if [ -z "$JAVA_HOME" ]; then
    JAVA_HOME=$(/usr/libexec/java_home 2>/dev/null || echo "")
fi

if [ -z "$JAVA_HOME" ]; then
    echo "❌ Error: JAVA_HOME not found"
    echo "Please install JDK or set JAVA_HOME environment variable"
    exit 1
fi

echo "✓ JAVA_HOME: $JAVA_HOME"

# Create output directory
mkdir -p "$OUTPUT_DIR"
echo "✓ Output directory: $OUTPUT_DIR"
echo ""

# Compile the JNI library
echo "Compiling stealth_jni.m..."
clang -dynamiclib \
    -o "$OUTPUT_DIR/libstealth.dylib" \
    "$SCRIPT_DIR/stealth_jni.m" \
    -framework Cocoa \
    -framework Foundation \
    -I"$JAVA_HOME/include" \
    -I"$JAVA_HOME/include/darwin" \
    -mmacosx-version-min=10.13 \
    -arch arm64 \
    -arch x86_64

if [ $? -eq 0 ]; then
    echo ""
    echo "✅ Build successful!"
    echo ""
    echo "📦 Library: $OUTPUT_DIR/libstealth.dylib"
    
    # Show library info
    echo ""
    echo "Library info:"
    file "$OUTPUT_DIR/libstealth.dylib"
    
    echo ""
    echo "🎉 Stealth Mode native library is ready!"
    echo ""
    echo "Next steps:"
    echo "  1. Run: ./gradlew :composeApp:run"
    echo "  2. Go to Settings"
    echo "  3. Toggle 'Hide from Screen Capture'"
    echo "  4. Test with screenshot (Cmd+Shift+4)"
else
    echo ""
    echo "❌ Build failed"
    exit 1
fi
