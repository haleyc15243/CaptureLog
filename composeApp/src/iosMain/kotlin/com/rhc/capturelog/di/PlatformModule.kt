package com.rhc.capturelog.di

import com.rhc.capturelog.database.DatabaseDriverFactory
import org.koin.core.annotation.ComponentScan
import org.koin.core.annotation.Module
import org.koin.core.annotation.Single

@Module
@ComponentScan("com.rhc.capturelog")
actual class PlatformModule {
    @Single
    actual fun provideDatabaseDriverFactory(): DatabaseDriverFactory {
        return DatabaseDriverFactory()
    }
}