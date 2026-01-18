package com.rhc.capturelog.database

import android.content.Context
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import com.rhc.capturelog.AppDatabase

class AndroidDatabaseDriverFactory(
    private val context: Context
) : DatabaseDriverFactory {
    override fun createDriver(): SqlDriver {
        println("Android SQLite Driver used")
        return AndroidSqliteDriver(AppDatabase.Schema, context, "app.db")
    }
}