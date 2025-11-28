package com.github.ericomonteiro.copilot.di

import app.cash.sqldelight.driver.jdbc.sqlite.JdbcSqliteDriver
import com.github.ericomonteiro.copilot.db.Database
import java.io.File

actual fun createDatabase(): Database {
    val databasePath = getDatabasePath()
    val driver = JdbcSqliteDriver("jdbc:sqlite:$databasePath")
    
    // Create schema if needed
    if (!File(databasePath).exists()) {
        Database.Schema.create(driver)
    }
    
    return Database(driver)
}

private fun getDatabasePath(): String {
    val userHome = System.getProperty("user.home")
    val appDir = File(userHome, ".interviewassistant")
    if (!appDir.exists()) {
        appDir.mkdirs()
    }
    return File(appDir, "database.db").absolutePath
}
