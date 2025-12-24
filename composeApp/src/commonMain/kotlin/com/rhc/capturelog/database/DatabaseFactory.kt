package com.rhc.capturelog.database

import app.cash.sqldelight.db.SqlDriver
import com.rhc.capturelog.AppDatabase
import com.rhc.capturelog.Daily_note
import com.rhc.capturelog.Note_tag
import com.rhc.capturelog.data.DailyNoteType
import com.rhc.capturelog.data.Tag
import com.rhc.capturelog.database.common.enumIntValue
import com.rhc.capturelog.database.common.inlineValue
import com.rhc.capturelog.database.dailyNote.DailyNoteDataSource
import com.rhc.capturelog.database.dailyNote.DailyNoteDataSourceImpl
import kotlin.time.Instant
import kotlin.uuid.Uuid

class DatabaseFactory(driverFactory: DatabaseDriverFactory) {

    private fun createDatabase(driver: SqlDriver) = AppDatabase.invoke(
        driver,
        dailyNoteAdapter,
        noteTagAdapter
    )

    private val dailyNoteAdapter = Daily_note.Adapter(
        note_idAdapter = inlineValue({ it.toHexString() }, { Uuid.parseHex(it)}),
        typeAdapter = enumIntValue<DailyNoteType>(),
        timestampAdapter = inlineValue({ it.toEpochMilliseconds() }, { Instant.fromEpochMilliseconds(it)})
    )

    private val noteTagAdapter = Note_tag.Adapter(
        note_idAdapter = inlineValue({ it.toHexString() }, { Uuid.parseHex(it)}),
        tagAdapter = inlineValue({ it.name }, { Tag(it) })
    )

    private val database by lazy { createDatabase(driverFactory.createDriver()) }

    internal fun dailyNoteDataSource(): DailyNoteDataSource = DailyNoteDataSourceImpl(database.daily_noteQueries)
}