package com.rhc.capturelog.data.capture

import kotlinx.serialization.Serializable
import kotlin.jvm.JvmInline
import kotlin.time.Instant
import kotlin.uuid.Uuid

@Serializable
data class Capture(
    val id: Id,
    val timestamp: Instant,
    val templateId: CaptureTemplate.Id,
    val title: String,
    val fields: Map<String, FieldValue>,
    val tags: List<Tag> = emptyList()
) {
    @JvmInline
    @Serializable
    value class Id(val value: Uuid)
}