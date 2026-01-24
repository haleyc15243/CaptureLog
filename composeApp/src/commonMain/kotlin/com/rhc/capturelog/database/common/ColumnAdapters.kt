package com.rhc.capturelog.database.common

import androidx.compose.ui.graphics.Color
import app.cash.sqldelight.ColumnAdapter
import com.rhc.capturelog.data.capture.Capture
import com.rhc.capturelog.data.capture.CaptureTemplate
import com.rhc.capturelog.data.capture.Tag
import com.rhc.capturelog.data.capture.TagColor
import com.rhc.capturelog.data.capture.TemplateIcon
import com.rhc.capturelog.data.dailynote.DailyNote
import com.rhc.capturelog.data.todo.QueueStatus
import com.rhc.capturelog.data.todo.TodoItem
import io.ktor.http.Url
import kotlin.time.Instant
import kotlin.uuid.Uuid

inline fun <reified T : Any, reified U : Any> inlineValue(
    crossinline toDb: (T) -> U,
    crossinline fromDb: (U) -> T
): ColumnAdapter<T, U> {
    return object : ColumnAdapter<T, U> {
        override fun decode(databaseValue: U) = fromDb(databaseValue)
        override fun encode(value: T) = toDb(value)
    }
}

inline fun <reified T : Enum<T>> enumStringValue(): ColumnAdapter<T, String> {
    return object : ColumnAdapter<T, String> {
        override fun decode(databaseValue: String): T = enumValues<T>().first { it.name == databaseValue }
        override fun encode(value: T): String = enumValues<T>().first { it == value }.name
    }
}

inline fun <reified T : Enum<T>> enumIntValue(): ColumnAdapter<T, Long> {
    return object : ColumnAdapter<T, Long> {
        override fun decode(databaseValue: Long): T = enumValues<T>().first { it.ordinal == databaseValue.toInt() }
        override fun encode(value: T): Long = value.ordinal.toLong()
    }
}

val urlAdapter = object : ColumnAdapter<Url, String> {
    override fun decode(databaseValue: String): Url = Url(databaseValue)
    override fun encode(value: Url): String = value.toString()
}

val instantAdapter = object : ColumnAdapter<Instant, Long> {
    override fun decode(databaseValue: Long): Instant = Instant.fromEpochMilliseconds(databaseValue)
    override fun encode(value: Instant): Long = value.toEpochMilliseconds()
}

val dailyNoteIdAdapter = inlineValue<DailyNote.Id, String>(
    { it.value.toHexString() },
    { DailyNote.Id(Uuid.parseHex(it)) }
)

val captureIdAdapter = inlineValue<Capture.Id, String>(
    { it.value.toHexString() },
    { Capture.Id(Uuid.parseHex(it)) }
)

val captureTemplateIdAdapter = inlineValue<CaptureTemplate.Id, String>(
    { it.value.toHexString() },
    { CaptureTemplate.Id(Uuid.parseHex(it)) }
)

val captureTemplateIconAdapter = enumIntValue<TemplateIcon>()

val todoItemIdAdapter = inlineValue<TodoItem.Id, String>(
    { it.value.toHexString() },
    { TodoItem.Id(Uuid.parseHex(it)) }
)

val tagIdAdapter = inlineValue<Tag.Id, String>(
    { it.value.toHexString() },
    { Tag.Id(Uuid.parseHex(it)) }
)

val queueStatusAdapter = enumIntValue<QueueStatus>()

val tagColorAdapter = enumIntValue<TagColor>()

val colorLongAdapter = inlineValue<Color, Long>(
    { it.value.toLong() },
    { Color(it) }
)