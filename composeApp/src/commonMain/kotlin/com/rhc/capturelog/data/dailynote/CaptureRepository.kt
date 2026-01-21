package com.rhc.capturelog.data.dailynote

import com.rhc.capturelog.database.dailyNote.CaptureDataSource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.Flow
import org.koin.core.annotation.Single

interface CaptureRepository {
    fun getAllCaptures(): List<Capture>
    fun allCapturesFlow(): Flow<List<Capture>>

    fun allTemplatesFlow(): Flow<List<CaptureTemplate>>
}

@Single
class CaptureRepositoryImpl(
    private val dailyNoteDataSource: CaptureDataSource,
): CaptureRepository {
    private val scope = CoroutineScope(SupervisorJob())

    override fun getAllCaptures(): List<Capture> = dailyNoteDataSource.getAllCaptures()
    override fun allCapturesFlow(): Flow<List<Capture>> = dailyNoteDataSource.allCapturesFlow()

    override fun allTemplatesFlow(): Flow<List<CaptureTemplate>> = dailyNoteDataSource.allTemplatesFlow()
}