package com.github.ericomonteiro.pirateparrotai.platform

/**
 * Enum representing the current operating system.
 */
enum class OperatingSystem {
    MACOS,
    WINDOWS,
    LINUX,
    UNKNOWN
}

/**
 * Platform-specific information provider.
 * Uses expect/actual pattern for multiplatform support.
 */
expect object PlatformInfo {
    /**
     * Returns the current operating system.
     */
    fun getOperatingSystem(): OperatingSystem
}

/**
 * Returns the modifier key name for the current platform.
 * - macOS: "Cmd"
 * - Windows/Linux: "Ctrl"
 */
fun getModifierKey(): String {
    return when (PlatformInfo.getOperatingSystem()) {
        OperatingSystem.MACOS -> "Cmd"
        else -> "Ctrl"
    }
}

/**
 * Returns the option/alt key name for the current platform.
 * - macOS: "Opt"
 * - Windows/Linux: "Alt"
 */
fun getOptionKey(): String {
    return when (PlatformInfo.getOperatingSystem()) {
        OperatingSystem.MACOS -> "Opt"
        else -> "Alt"
    }
}

/**
 * Formats a keyboard shortcut string for the current platform.
 * Example: formatShortcut("S") returns "Cmd+Shift+Opt+S" on macOS, "Ctrl+Shift+Alt+S" on Windows/Linux
 */
fun formatShortcut(key: String): String {
    val modifier = getModifierKey()
    val option = getOptionKey()
    return "$modifier+Shift+$option+$key"
}
