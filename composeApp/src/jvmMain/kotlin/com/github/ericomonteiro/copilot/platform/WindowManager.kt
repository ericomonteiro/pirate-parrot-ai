package com.github.ericomonteiro.copilot.platform

import com.sun.jna.Native
import com.sun.jna.Pointer
import com.sun.jna.platform.mac.CoreFoundation
import java.awt.Window
import javax.swing.SwingUtilities

/**
 * JVM implementation of WindowManager for macOS and Windows.
 * Uses JNI for macOS stealth mode and JNA for Windows.
 */
actual class WindowManager {
    private var currentWindow: Window? = null
    private var nativeLibraryLoaded = false
    
    init {
        // Try to load native library for stealth mode
        if (isMacOS()) {
            try {
                loadNativeLibrary()
                nativeLibraryLoaded = true
                println("WindowManager: Native stealth library loaded successfully")
            } catch (e: Exception) {
                println("WindowManager: Failed to load native library: ${e.message}")
                println("WindowManager: Stealth mode will not be available")
            }
        }
    }
    
    /**
     * Sets the AWT window to manage.
     * Should be called once the Compose window is created.
     */
    fun setWindow(window: Window) {
        currentWindow = window
    }
    
    /**
     * Load the native JNI library for stealth mode
     */
    private fun loadNativeLibrary() {
        val resourcePath = "/native/macos/libstealth.dylib"
        val stream = this::class.java.getResourceAsStream(resourcePath)
            ?: throw IllegalStateException("Native library not found in resources: $resourcePath")
        
        // Extract to temp file
        val tempFile = java.io.File.createTempFile("libstealth", ".dylib")
        tempFile.deleteOnExit()
        
        stream.use { input ->
            tempFile.outputStream().use { output ->
                input.copyTo(output)
            }
        }
        
        // Load the library
        System.load(tempFile.absolutePath)
    }
    
    /**
     * Native method declaration - implemented in stealth_jni.m
     */
    private external fun nativeSetHideFromCapture(hide: Boolean)
    
    actual fun setHideFromCapture(hide: Boolean) {
        try {
            when {
                isMacOS() -> setHideFromCaptureMacOSNative(hide)
                isWindows() -> setHideFromCaptureWindows(currentWindow ?: findActiveWindow() ?: return, hide)
                else -> println("WindowManager: Stealth mode not supported on this platform")
            }
        } catch (e: Exception) {
            println("WindowManager: Failed to set hide from capture: ${e.message}")
            e.printStackTrace()
        }
    }
    
    private fun setHideFromCaptureMacOSNative(hide: Boolean) {
        if (!nativeLibraryLoaded) {
            println("WindowManager: Native library not loaded")
            println("WindowManager: Stealth mode requires native compilation")
            println("WindowManager: Run: ./native/macos/build_jni.sh")
            return
        }
        
        try {
            // Call native JNI method
            nativeSetHideFromCapture(hide)
            println("WindowManager: Stealth mode ${if (hide) "enabled" else "disabled"}")
        } catch (e: UnsatisfiedLinkError) {
            println("WindowManager: Native method not found: ${e.message}")
            e.printStackTrace()
        } catch (e: Exception) {
            println("WindowManager: Failed to set stealth mode: ${e.message}")
            e.printStackTrace()
        }
    }
    
    
    private fun setHideFromCaptureWindows(window: Window, hide: Boolean) {
        try {
            // Get the native window handle (HWND)
            val windowClass = window.javaClass
            val peerField = windowClass.superclass.getDeclaredField("peer")
            peerField.isAccessible = true
            val peer = peerField.get(window)
            
            val platformWindowClass = peer.javaClass
            val getHWndMethod = platformWindowClass.getDeclaredMethod("getHWnd")
            getHWndMethod.isAccessible = true
            val hwnd = getHWndMethod.invoke(peer) as Long
            
            if (hwnd == 0L) {
                println("WindowManager: Failed to get HWND")
                return
            }
            
            // Use JNA to call SetWindowDisplayAffinity
            val user32 = User32Extended.INSTANCE
            val affinity = if (hide) 0x00000011 else 0x00000000 // WDA_EXCLUDEFROMCAPTURE or WDA_NONE
            val success = user32.SetWindowDisplayAffinity(Pointer(hwnd), affinity)
            
            if (success) {
                println("WindowManager: Windows stealth mode ${if (hide) "enabled" else "disabled"}")
            } else {
                println("WindowManager: Failed to set Windows stealth mode")
            }
        } catch (e: Exception) {
            println("WindowManager: Failed to set Windows stealth mode: ${e.message}")
            e.printStackTrace()
        }
    }
    
    private fun findActiveWindow(): Window? {
        return try {
            Window.getWindows().firstOrNull { it.isShowing }
        } catch (e: Exception) {
            null
        }
    }
    
    private fun isMacOS(): Boolean {
        return System.getProperty("os.name").lowercase().contains("mac")
    }
    
    private fun isWindows(): Boolean {
        return System.getProperty("os.name").lowercase().contains("win")
    }
}


/**
 * JNA interface for Windows User32 extended functions
 */
private interface User32Extended : com.sun.jna.Library {
    fun SetWindowDisplayAffinity(hwnd: Pointer, affinity: Int): Boolean
    
    companion object {
        val INSTANCE: User32Extended = Native.load("user32", User32Extended::class.java) as User32Extended
    }
}
