package com.rhc.capturelog.data.dailynote

import androidx.compose.ui.graphics.Color
import com.rhc.capturelog.data.capture.Capture
import com.rhc.capturelog.data.capture.Tag
import kotlin.uuid.Uuid
import kotlinx.serialization.Serializable
import kotlin.jvm.JvmInline
import kotlin.time.Clock.System.now
import kotlin.time.Instant

@Serializable
data class DailyNote(
    val id: Id = Id(),
    val captures: List<Capture> = emptyList(),
    val title: String,
    val content: List<String>,
    val tags: List<Tag> = emptyList(),
    val createdAt: Instant = now(),
    val updatedAt: Instant = now()
) {
    @JvmInline
    @Serializable
    value class Id(val value: Uuid) {
        constructor() : this(Uuid.random())
    }
}

enum class DailyNoteType(val color: Color, val title: String) {
    CODE_REVIEW(Color(0xFF10B981), "CODE REVIEW"),
    ARTICLE(Color(0xFFF59E0B), "ARTICLE"),
    QUICK_NOTE(Color(0xFF6366F1), "QUICK NOTE")
}