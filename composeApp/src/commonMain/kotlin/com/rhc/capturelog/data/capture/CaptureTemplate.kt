package com.rhc.capturelog.data.capture

import androidx.compose.ui.graphics.Color
import com.rhc.capturelog.core.utility.UuidUtils
import com.rhc.capturelog.ui.theme.AmberLightMode
import com.rhc.capturelog.ui.theme.EmeraldLightMode
import com.rhc.capturelog.ui.theme.IndigoLightMode
import com.rhc.capturelog.ui.theme.VioletDarkMode
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlin.jvm.JvmInline
import kotlin.time.Clock.System.now
import kotlin.time.Instant
import kotlin.uuid.Uuid

@Serializable
data class CaptureTemplate(
    val id: Id,
    val name: String,
    val icon: String,
    @Serializable(with = ColorLongSerializer::class)
    val color: Color,
    val fields: List<TemplateField>,
    val isDefault: Boolean = false,
    val isArchived: Boolean = false,
    val createdAt: Instant = now(),
    val updatedAt: Instant = now()
) {
    @JvmInline
    @Serializable
    value class Id(val value: Uuid) {
        constructor(stringVal: String) : this (UuidUtils.stringToUuid(stringVal))
    }
}

@Serializable
data class TemplateField(
    val id: String,
    val label: String,
    val type: FieldType,
    val required: Boolean,
    val placeholder: String = "",
    val options: List<String>? = null  // For dropdown/multi-select
)

@Serializable
enum class FieldType {
    SHORT_TEXT,      // Single line
    LONG_TEXT,       // Multi-line
    URL,             // URL with validation
    CHECKBOX_LIST,   // Action items
    DROPDOWN,        // Single selection
    MULTI_SELECT,    // Multiple selections
    NUMBER,          // Numeric input
    DATE,            // Date picker
    RATING           // 1-5 stars
}

@Serializable
sealed class FieldValue {
    @Serializable
    data class Text(val value: String) : FieldValue()

    @Serializable
    data class CheckList(val items: List<CheckItem>) : FieldValue()

    @Serializable
    data class Selection(val selected: List<String>) : FieldValue()

    @Serializable
    data class Numeric(val value: Double) : FieldValue()

    @Serializable
    data class DateValue(val value: Instant) : FieldValue()

    @Serializable
    data class Rating(val value: Int) : FieldValue()  // 1-5
}

@Serializable
data class CheckItem(
    val id: String,
    val text: String,
    val completed: Boolean = false,
    val completedAt: Instant? = null
)

object ColorLongSerializer : KSerializer<Color> {
    override val descriptor: SerialDescriptor
        get() = PrimitiveSerialDescriptor("Color", PrimitiveKind.LONG)

    override fun serialize(encoder: Encoder, value: Color) {
        encoder.encodeLong(value.value.toLong())
    }
    override fun deserialize(decoder: Decoder): Color {
        return Color(decoder.decodeLong())
    }
}

object DefaultCaptureTemplates {
    val QuickNotes = CaptureTemplate(
        id = CaptureTemplate.Id("quick-note"),
        name = "Quick Note",
        icon = "edit",
        color = IndigoLightMode,
        fields = listOf(
            TemplateField(
                id = "content",
                label = "Note",
                type = FieldType.LONG_TEXT,
                required = true
            )
        ),
        isDefault = true
    )
    val CodeReview = CaptureTemplate(
        id = CaptureTemplate.Id("code-review"),
        name = "Code Review",
        icon = "code",
        color = EmeraldLightMode,
        fields = listOf(
            TemplateField(
                id = "pr_url",
                label = "PR/MR URL",
                type = FieldType.URL,
                required = false
            ),
            TemplateField(
                id = "learnings",
                label = "What I Learned",
                type = FieldType.CHECKBOX_LIST,
                required = true
            ),
            TemplateField(
                id = "action_items",
                label = "Action Items",
                type = FieldType.CHECKBOX_LIST,
                required = false
            )
        ),
        isDefault = true
    )
    val ArticleLink = CaptureTemplate(
        id = CaptureTemplate.Id("article"),
        name = "Article",
        icon = "link",
        color = AmberLightMode,
        fields = listOf(
            TemplateField(
                id = "url",
                label = "URL",
                type = FieldType.URL,
                required = true
            ),
            TemplateField(
                id = "source",
                label = "Source",
                type = FieldType.SHORT_TEXT,
                required = false,
                placeholder = "e.g. Android Weekly, Medium"
            ),
            TemplateField(
                id = "status",
                label = "Status",
                type = FieldType.DROPDOWN,
                required = true,
                options = listOf("To Read", "Reading", "Read")
            ),
            TemplateField(
                id = "notes",
                label = "Key Takeaways",
                type = FieldType.LONG_TEXT,
                required = false
            )
        ),
        isDefault = true
    )
    val Learning = CaptureTemplate(
        id = CaptureTemplate.Id("learning"),
        name = "Learning",
        icon = "lightbulb",
        color = VioletDarkMode,
        fields = listOf(
            TemplateField(
                id = "what_tried",
                label = "What I Tried",
                type = FieldType.SHORT_TEXT,
                required = true
            ),
            TemplateField(
                id = "outcome",
                label = "Outcome",
                type = FieldType.LONG_TEXT,
                required = true
            ),
            TemplateField(
                id = "confidence",
                label = "Confidence Level",
                type = FieldType.RATING,
                required = false
            )
        ),
        isDefault = true
    )
}