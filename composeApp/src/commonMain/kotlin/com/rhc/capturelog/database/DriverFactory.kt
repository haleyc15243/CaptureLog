package com.rhc.capturelog.database

import app.cash.sqldelight.db.SqlDriver
import coil3.PlatformContext

expect class DatabaseDriverFactory(context: PlatformContext) {
    fun createDriver(): SqlDriver
}