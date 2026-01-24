package com.rhc.capturelog.database

import app.cash.sqldelight.db.SqlDriver
import com.rhc.capturelog.AppDatabase
import com.rhc.capturelog.Capture_tag
import com.rhc.capturelog.Capture_template
import com.rhc.capturelog.Capture as CaptureTable
import com.rhc.capturelog.Daily_note
import com.rhc.capturelog.Tag
import com.rhc.capturelog.Todo_item
import com.rhc.capturelog.database.common.captureIdAdapter
import com.rhc.capturelog.database.common.captureTemplateIdAdapter
import com.rhc.capturelog.database.common.colorLongAdapter
import com.rhc.capturelog.database.common.dailyNoteIdAdapter
import com.rhc.capturelog.database.common.tagColorAdapter
import com.rhc.capturelog.database.common.instantAdapter
import com.rhc.capturelog.database.common.queueStatusAdapter
import com.rhc.capturelog.database.common.tagIdAdapter
import com.rhc.capturelog.database.common.todoItemIdAdapter
import com.rhc.capturelog.database.capture.CaptureDataSource
import com.rhc.capturelog.database.capture.CaptureDataSourceImpl
import com.rhc.capturelog.database.common.captureTemplateIconAdapter
import kotlinx.coroutines.CoroutineDispatcher

class DatabaseFactory(driverFactory: DatabaseDriverFactory) {

    private val dailyNoteAdapter = Daily_note.Adapter(
        idAdapter = dailyNoteIdAdapter,
        dateAdapter = instantAdapter,
        createdAtAdapter = instantAdapter,
        updatedAtAdapter = instantAdapter,
    )

    private val captureAdapter = CaptureTable.Adapter(
        idAdapter = captureIdAdapter,
        dailyNoteIdAdapter = dailyNoteIdAdapter,
        timestampAdapter = instantAdapter,
        templateIdAdapter = captureTemplateIdAdapter,
    )

    private val captureTagAdapter = Capture_tag.Adapter(
        captureIdAdapter = captureIdAdapter,
        tagIdAdapter = tagIdAdapter
    )

    private val captureTemplateAdapter = Capture_template.Adapter(
        idAdapter = captureTemplateIdAdapter,
        iconAdapter = captureTemplateIconAdapter,
        colorAdapter = colorLongAdapter,
        createdAtAdapter = instantAdapter,
        updatedAtAdapter = instantAdapter
    )

    private val tagAdapter = Tag.Adapter(
        idAdapter = tagIdAdapter,
        colorAdapter = tagColorAdapter
    )

    private val todoItemAdapter = Todo_item.Adapter(
        idAdapter = todoItemIdAdapter,
        statusAdapter = queueStatusAdapter,
        dateAddedAdapter = instantAdapter,
        completedAtAdapter = instantAdapter
    )

    private fun createDatabase(driver: SqlDriver) = AppDatabase(
        driver,
        captureAdapter = captureAdapter,
        capture_tagAdapter = captureTagAdapter,
        capture_templateAdapter = captureTemplateAdapter,
        daily_noteAdapter = dailyNoteAdapter,
        tagAdapter = tagAdapter,
        todo_itemAdapter = todoItemAdapter
    ).also {
        println("Database created $it")
    }

    private val database by lazy { createDatabase(driverFactory.createDriver()) }

    internal fun dailyNoteDataSource(
        ioDispatcher: CoroutineDispatcher
    ): CaptureDataSource = CaptureDataSourceImpl(
        database.captureQueries,
        database.capture_templateQueries,
        database.daily_noteQueries,
        database.tagQueries,
        ioDispatcher
    )
}