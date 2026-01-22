package com.rhc.capturelog.data.todo

import kotlinx.serialization.Serializable
import kotlin.jvm.JvmInline
import kotlin.time.Instant
import kotlin.uuid.Uuid

@Serializable
data class TodoItem(
    val id: Id,
    val title: String,
    val description: String,
    val source: String,
    val priority: Int,
    val tags: List<String> = emptyList(),
    val status: QueueStatus,
    val dateAdded: Instant,
    val notes: String = "",
    val completedAt: Instant? = null,
    val metadata: Map<String, String> = emptyMap()
) {
    @JvmInline
    @Serializable
    value class Id(val value: Uuid)
}

@Serializable
enum class QueueStatus {
    TO_DO,
    IN_PROGRESS,
    COMPLETED,
    ARCHIVED
}
