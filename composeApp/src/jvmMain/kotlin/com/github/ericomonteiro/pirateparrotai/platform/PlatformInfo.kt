package com.github.ericomonteiro.pirateparrotai.platform

actual object PlatformInfo {
    actual fun getOperatingSystem(): OperatingSystem {
        val osName = System.getProperty("os.name").lowercase()
        return when {
            osName.contains("mac") || osName.contains("darwin") -> OperatingSystem.MACOS
            osName.contains("win") -> OperatingSystem.WINDOWS
            osName.contains("nux") || osName.contains("nix") || osName.contains("aix") -> OperatingSystem.LINUX
            else -> OperatingSystem.UNKNOWN
        }
    }
}
