package com.github.ericomonteiro.copilot.screenshot

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.awt.Rectangle
import java.awt.Robot
import java.awt.Toolkit
import java.io.ByteArrayOutputStream
import javax.imageio.ImageIO

actual suspend fun captureScreenshot(): Result<String> = withContext(Dispatchers.IO) {
    runCatching {
        val robot = Robot()
        val screenSize = Toolkit.getDefaultToolkit().screenSize
        val screenRect = Rectangle(screenSize)
        val screenshot = robot.createScreenCapture(screenRect)
        
        // Convert to PNG bytes
        val outputStream = ByteArrayOutputStream()
        ImageIO.write(screenshot, "png", outputStream)
        val bytes = outputStream.toByteArray()
        
        // Convert to base64
        bytes.toBase64()
    }
}
