package com.rhc.capturelog.di

import com.rhc.capturelog.database.DatabaseDriverFactory
import com.rhc.capturelog.database.DatabaseModule
import org.koin.core.annotation.ComponentScan
import org.koin.core.annotation.Module
import org.koin.core.annotation.Single

@Module
@ComponentScan("com.rhc.capturelog")
expect class PlatformModule() {
    @Single
    fun provideDatabaseDriverFactory(): DatabaseDriverFactory
}

@Module([
    PlatformModule::class,
    DatabaseModule::class
])
internal class AppModule