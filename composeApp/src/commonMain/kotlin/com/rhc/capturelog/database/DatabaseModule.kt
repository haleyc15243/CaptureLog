package com.rhc.capturelog.database

import com.rhc.capturelog.database.dailyNote.DailyNoteDataSource
import org.koin.core.annotation.Module
import org.koin.core.annotation.Provided
import org.koin.core.annotation.Single

@Module
class DatabaseModule {
    @Single
    fun provideDatabaseFactory(
        @Provided
        driverFactory: DatabaseDriverFactory
    ): DatabaseFactory = DatabaseFactory(driverFactory)

    @Single
    fun provideDailyNoteDataSource(
        databaseFactory: DatabaseFactory
    ): DailyNoteDataSource = databaseFactory.dailyNoteDataSource()
}