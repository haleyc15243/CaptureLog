package com.rhc.capturelog.di

import android.content.Context
import com.rhc.capturelog.database.DatabaseDriverFactory
import org.koin.core.annotation.ComponentScan
import org.koin.core.annotation.Module
import org.koin.core.annotation.Single
import org.koin.mp.KoinPlatformTools

@Module
@ComponentScan("com.rhc.capturelog")
actual class PlatformModule {
    @Single
    actual fun provideDatabaseDriverFactory(): DatabaseDriverFactory {
        val context = KoinPlatformTools.defaultContext().get().get<Context>()
        return DatabaseDriverFactory(context)
    }
}