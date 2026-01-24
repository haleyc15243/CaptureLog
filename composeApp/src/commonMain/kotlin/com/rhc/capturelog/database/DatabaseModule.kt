package com.rhc.capturelog.database

import com.rhc.capturelog.core.dispatchers.IODispatcher
import com.rhc.capturelog.database.capture.CaptureDataSource
import kotlinx.coroutines.CoroutineDispatcher
import org.koin.core.annotation.ComponentScan
import org.koin.core.annotation.Module
import org.koin.core.annotation.Provided
import org.koin.core.annotation.Single

@Module
@ComponentScan("com.rhc.capturelog.database")
class DatabaseModule {
    @Single
    fun provideDatabaseFactory(
        @Provided
        driverFactory: DatabaseDriverFactory
    ): DatabaseFactory = DatabaseFactory(driverFactory)

    @Single
    fun provideDailyNoteDataSource(
        @IODispatcher ioDispatcher: CoroutineDispatcher,
        databaseFactory: DatabaseFactory
    ): CaptureDataSource = databaseFactory.dailyNoteDataSource(ioDispatcher)
}