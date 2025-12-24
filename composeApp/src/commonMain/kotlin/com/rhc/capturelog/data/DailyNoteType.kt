package com.rhc.capturelog.data

import androidx.compose.ui.graphics.Color
import kotlin.jvm.JvmInline
import kotlin.time.Instant
import kotlin.uuid.Uuid

data class DailyNote(
    val id: Uuid,
    val type: DailyNoteType,
    val title: String,
    val content: List<String>,
    val tags: List<Tag>,
    val timestamp: Instant
)

@JvmInline
value class Tag(val name: String)

enum class DailyNoteType(val color: Color, val title: String) {
    CODE_REVIEW(Color(0xFF10B981), "CODE REVIEW"),
    ARTICLE(Color(0xFFF59E0B), "ARTICLE"),
    QUICK_NOTE(Color(0xFF6366F1), "QUICK NOTE")
}