package com.rhc.capturelog.di

import com.rhc.capturelog.database.DatabaseModule
import org.koin.core.annotation.Module
import org.koin.core.module.Module as KoinModule

internal expect val platformModule: KoinModule

@Module([
    PlatformModule::class,
    DatabaseModule::class
])
internal class AppModule