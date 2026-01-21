package com.rhc.capturelog.database.dailyNote

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import com.rhc.capturelog.Capture as DbCapture
import com.rhc.capturelog.CaptureQueries
import com.rhc.capturelog.Capture_template as DbCaptureTemplate
import com.rhc.capturelog.Capture_templateQueries
import com.rhc.capturelog.Daily_noteQueries
import com.rhc.capturelog.TagQueries
import com.rhc.capturelog.data.dailynote.Capture
import com.rhc.capturelog.data.dailynote.CaptureTemplate
import com.rhc.capturelog.data.dailynote.Tag
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.serialization.json.Json
import kotlin.coroutines.CoroutineContext

interface CaptureDataSource {
    fun getAllCaptures(): List<Capture>
    fun allCapturesFlow(): Flow<List<Capture>>

    fun allTemplatesFlow(): Flow<List<CaptureTemplate>>
}

internal class CaptureDataSourceImpl(
    private val captureQueries: CaptureQueries,
    private val templateQueries: Capture_templateQueries,
    private val noteQueries: Daily_noteQueries,
    private val tagQueries: TagQueries,
    private val coroutineContext: CoroutineContext
) : CaptureDataSource {
    val json = Json {
        ignoreUnknownKeys = true
    }

    override fun getAllCaptures(): List<Capture> {
        return captureQueries.selectAll().executeAsList().map {
            val allTags = tagQueries.selectAll().executeAsList().map { tag ->
                Tag(tag.id, tag.name, tag.color, tag.usageCount.toInt())
            }
            it.buildCapture(allTags)
        }
    }

    override fun allCapturesFlow(): Flow<List<Capture>> {
        return captureQueries.selectAll().asFlow()
            .mapToList(coroutineContext).map { captureList ->
                val allTags = tagQueries.selectAll().executeAsList().map { tag ->
                    Tag(tag.id, tag.name, tag.color, tag.usageCount.toInt())
                }
                captureList.map { it.buildCapture(allTags) }
            }
    }

    override fun allTemplatesFlow(): Flow<List<CaptureTemplate>> {
        return templateQueries.selectAll().asFlow()
            .mapToList(coroutineContext).map { templateList ->
                templateList.map { it.buildCaptureTemplate() }
            }
    }

    fun DbCapture.buildCapture(allTags: List<Tag>): Capture {
        val tagIds = captureQueries.selectTagByCaptureId(id)
            .executeAsList()
        val tags = allTags.filter { it.id in tagIds }
        return Capture(
            id,
            timestamp,
            templateId,
            title,
            json.decodeFromString(fieldsJson),
            tags
        )
    }

    fun DbCaptureTemplate.buildCaptureTemplate(): CaptureTemplate {
        return CaptureTemplate(
            id = id,
            name = name,
            icon = icon,
            color = color,
            fields = json.decodeFromString(fieldsJson),
            isDefault = isDefault,
            isArchived = isArchived,
            createdAt = createdAt,
            updatedAt = updatedAt
        )
    }
}