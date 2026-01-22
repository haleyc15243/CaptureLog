package com.rhc.capturelog.data.capture

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Article
import androidx.compose.material.icons.automirrored.filled.Chat
import androidx.compose.material.icons.automirrored.filled.Notes
import androidx.compose.material.icons.automirrored.filled.ReceiptLong
import androidx.compose.material.icons.filled.Book
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.BugReport
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Code
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Flag
import androidx.compose.material.icons.filled.Groups
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.material.icons.filled.Link
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.QuestionMark
import androidx.compose.material.icons.filled.Restaurant
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.filled.Work
import androidx.compose.ui.graphics.vector.ImageVector

/**
 * A type-safe enum representing the icons that can be used for a CaptureTemplate.
 * This ensures that only valid, pre-defined icons can be associated with a template.
 */
enum class TemplateIcon {
    // Original Icons
    QUICK_NOTE,
    CODE_REVIEW,
    ARTICLE,
    LEARNING,

    // Expanded Icons
    TASK,
    BOOKMARK,
    LINK,
    MEETING,
    IDEA,
    JOURNAL,
    EXPENSE,
    GOAL,
    CONTACT,
    LOCATION,
    EVENT,
    MESSAGE,
    PHOTO,
    AUDIO,
    BUG,
    INFO,
    QUOTE,
    RECIPE,
    SHOPPING_LIST,
    WORK_LOG,

    // Default
    UNKNOWN;

    /**
     * Converts the enum value into its corresponding Material Design [ImageVector].
     *
     * @return The matching [ImageVector] for the enum constant.
     */
    fun toImageVector(): ImageVector {
        return when (this) {
            // Original
            QUICK_NOTE -> Icons.Filled.Edit
            CODE_REVIEW -> Icons.Filled.Code
            ARTICLE -> Icons.AutoMirrored.Filled.Article
            LEARNING -> Icons.Filled.Lightbulb

            // Expanded
            TASK -> Icons.Filled.CheckCircle
            BOOKMARK -> Icons.Filled.Bookmark
            LINK -> Icons.Filled.Link
            MEETING -> Icons.Filled.Groups
            IDEA -> Icons.Filled.Lightbulb // Can reuse icons for similar concepts
            JOURNAL -> Icons.Filled.Book
            EXPENSE -> Icons.AutoMirrored.Filled.ReceiptLong
            GOAL -> Icons.Filled.Flag
            CONTACT -> Icons.Filled.Person
            LOCATION -> Icons.Filled.LocationOn
            EVENT -> Icons.Filled.CalendarMonth
            MESSAGE -> Icons.AutoMirrored.Filled.Chat
            PHOTO -> Icons.Filled.CameraAlt
            AUDIO -> Icons.Filled.Mic
            BUG -> Icons.Filled.BugReport
            INFO -> Icons.Filled.Info
            QUOTE -> Icons.AutoMirrored.Filled.Notes // Good for block quotes
            RECIPE -> Icons.Filled.Restaurant
            SHOPPING_LIST -> Icons.Filled.ShoppingCart
            WORK_LOG -> Icons.Filled.Work

            // Default
            UNKNOWN -> Icons.Filled.QuestionMark
        }
    }

    companion object {
        /**
         * Safely converts a string name into a [TemplateIcon] enum.
         * If the string does not match any enum constant, it defaults to [UNKNOWN].
         * This is useful for deserializing data from a database or API.
         *
         * @param name The string representation of the icon.
         * @return The corresponding [TemplateIcon] enum.
         */
        fun fromString(name: String?): TemplateIcon {
            return try {
                if (name == null) UNKNOWN else valueOf(name)
            } catch (e: IllegalArgumentException) {
                UNKNOWN
            }
        }
    }
}
