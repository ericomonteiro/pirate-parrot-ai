package com.github.ericomonteiro.copilot.screenshot

import java.util.Base64

expect suspend fun captureScreenshot(): Result<String>

fun ByteArray.toBase64(): String {
    return Base64.getEncoder().encodeToString(this)
}
