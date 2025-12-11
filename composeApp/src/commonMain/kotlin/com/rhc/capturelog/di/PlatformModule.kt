package com.rhc.capturelog.di

import com.rhc.capturelog.database.DatabaseModule
import org.koin.core.annotation.ComponentScan
import org.koin.core.annotation.Module

@Module
@ComponentScan("com.rhc.capturelog")
internal expect class PlatformModule()

@Module([
    DatabaseModule::class,
    PlatformModule::class
])
internal class AppModule