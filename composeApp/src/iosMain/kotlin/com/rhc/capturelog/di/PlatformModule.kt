package com.rhc.capturelog.di

import com.rhc.capturelog.database.DatabaseDriverFactory
import com.rhc.capturelog.database.IosDatabaseDriverFactory
import org.koin.dsl.module

internal actual val platformModule = module {
    single<DatabaseDriverFactory> { IosDatabaseDriverFactory() }
}