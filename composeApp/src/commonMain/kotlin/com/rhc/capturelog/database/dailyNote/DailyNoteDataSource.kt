package com.rhc.capturelog.database.dailyNote

import com.rhc.capturelog.Daily_noteQueries
import com.rhc.capturelog.data.DailyNote

interface DailyNoteDataSource {
    fun getDailyNotes(): List<DailyNote>
}

internal class DailyNoteDataSourceImpl(
    private val queries: Daily_noteQueries
) : DailyNoteDataSource {
    override fun getDailyNotes(): List<DailyNote> {
        return queries.selectAllNotes().executeAsList().map {
            val tags = queries.selectTagsByNoteId(it.note_id).executeAsList()
            DailyNote(
                it.note_id,
                it.type,
                it.title,
                it.content?.let { listOf(it) }.orEmpty(), // TODO
                tags,
                it.timestamp
            )
        }
    }
}