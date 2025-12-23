package com.rhc.capturelog.database

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import coil3.PlatformContext
import com.rhc.capturelog.AppDatabase

actual class DatabaseDriverFactory actual constructor(val context: PlatformContext) {
    actual fun createDriver(): SqlDriver {
        return AndroidSqliteDriver(AppDatabase.Schema, context, "app.db")
    }
}