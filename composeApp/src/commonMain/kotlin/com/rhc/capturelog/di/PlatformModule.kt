package com.rhc.capturelog.di

import com.rhc.capturelog.core.dispatchers.DispatcherModule
import com.rhc.capturelog.core.state.AppStateModule
import com.rhc.capturelog.data.DataModule
import com.rhc.capturelog.database.DatabaseModule
import com.rhc.capturelog.features.FeatureModule
import org.koin.core.annotation.Module
import org.koin.core.module.Module as KoinModule

internal expect val platformModule: KoinModule

@Module([
    AppStateModule::class,
    DataModule::class,
    DatabaseModule::class,
    DispatcherModule::class,
    FeatureModule::class,
])
internal class AppModule