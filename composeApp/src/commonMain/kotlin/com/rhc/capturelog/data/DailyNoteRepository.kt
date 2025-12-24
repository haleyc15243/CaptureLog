package com.rhc.capturelog.data

import com.rhc.capturelog.database.dailyNote.DailyNoteDataSource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.Flow
import org.koin.core.annotation.Single

@Single
class DailyNoteRepository(
    private val dailyNoteDataSource: DailyNoteDataSource,
) {
    private val scope = CoroutineScope(SupervisorJob())

    fun getDailyNotes(): List<DailyNote> = dailyNoteDataSource.getDailyNotes()
}
