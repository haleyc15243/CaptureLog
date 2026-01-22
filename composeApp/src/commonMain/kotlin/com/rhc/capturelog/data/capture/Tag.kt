package com.rhc.capturelog.data.capture

import kotlinx.serialization.Serializable
import kotlin.jvm.JvmInline
import kotlin.uuid.Uuid

@Serializable
data class Tag(
    val id: Id = Id(),
    val name: String,
    val color: TagColor = defaultColorFromName(name),
    val usageCount: Int = 0
) {

    @JvmInline
    @Serializable
    value class Id(val value: Uuid) {
        constructor() : this(Uuid.random())
    }

    companion object {
        /**
         * Determines a default TagColor based on the tag's name.
         * This function is private and only accessible within the Tag class,
         * primarily for default constructor parameter initialization.
         */
        private fun defaultColorFromName(name: String): TagColor {
            return when (name.lowercase()) {
                "code review" -> TagColor.BLUE
                "article" -> TagColor.GREEN
                "learning" -> TagColor.PURPLE
                "quick note" -> TagColor.YELLOW
                else -> TagColor.DEFAULT
            }
        }
    }
}

@Serializable
enum class TagColor {
    DEFAULT,
    BLUE,
    GREEN,
    YELLOW,
    RED,
    PURPLE,
    ORANGE,
    PINK
}