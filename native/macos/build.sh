#!/bin/bash

# Build script for macOS stealth mode utility

set -e

SCRIPT_DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"
OUTPUT_DIR="$SCRIPT_DIR/../../composeApp/src/jvmMain/resources/native/macos"

echo "Building stealth mode utility for macOS..."

# Create output directory
mkdir -p "$OUTPUT_DIR"

# Compile the Objective-C code
clang -framework Cocoa -framework Foundation \
    -o "$OUTPUT_DIR/stealth_mode" \
    "$SCRIPT_DIR/stealth_mode.m"

# Make it executable
chmod +x "$OUTPUT_DIR/stealth_mode"

echo "✅ Build complete: $OUTPUT_DIR/stealth_mode"
echo ""
echo "Test with:"
echo "  $OUTPUT_DIR/stealth_mode list"
echo "  $OUTPUT_DIR/stealth_mode enable"
echo "  $OUTPUT_DIR/stealth_mode disable"
