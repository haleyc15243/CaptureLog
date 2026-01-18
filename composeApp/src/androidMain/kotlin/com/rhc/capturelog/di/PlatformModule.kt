package com.rhc.capturelog.di

import com.rhc.capturelog.database.AndroidDatabaseDriverFactory
import com.rhc.capturelog.database.DatabaseDriverFactory
import org.koin.dsl.module
import org.koin.core.module.Module as KoinModule

internal actual val platformModule: KoinModule = module {
    single<DatabaseDriverFactory> { AndroidDatabaseDriverFactory(get()) }
}