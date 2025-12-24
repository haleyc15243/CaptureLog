package com.rhc.capturelog.features.dailynotes

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.repeatOnLifecycle
import com.rhc.capturelog.core.state.AppViewModel
import com.rhc.capturelog.data.DailyNote
import com.rhc.capturelog.data.DailyNoteType
import com.rhc.capturelog.data.Tag
import com.rhc.capturelog.ui.theme.CaptureLogExtendedTheme
import com.rhc.capturelog.ui.theme.CaptureLogIndigo
import com.rhc.capturelog.ui.theme.CaptureLogTheme
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.koinInject
import org.koin.compose.viewmodel.koinViewModel
import kotlin.time.Clock.System.now
import kotlin.time.Duration.Companion.days
import kotlin.time.Duration.Companion.hours
import kotlin.uuid.Uuid

@Composable
fun DailyNotesScreen(
    paddingValues: PaddingValues
) {
    val appViewModel = koinViewModel<AppViewModel>()
    val lifecycleOwner = LocalLifecycleOwner.current
    LaunchedEffect(Unit) {
        lifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
            appViewModel.appStateEvents.collect {
//                temp(it)
            }
        }
    }
    val viewModel = koinInject<DailyNoteViewModel>()
    val dailyNotesFlow = viewModel.dailyNotes.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        appViewModel.setTitle("Test title")
    }

    DailyNoteList(
        notes = dailyNotesFlow.value,
        modifier = Modifier.padding(paddingValues)
    )
}

@Composable
fun DailyNoteList(notes: List<DailyNote>, modifier: Modifier = Modifier) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(notes) { note ->
            DailyNoteCard(note)
        }
    }
}

@Composable
fun DailyNoteCard(note: DailyNote) {
    val shape = RoundedCornerShape(8.dp)
    Card(
        modifier = Modifier.border(
            width = 2.dp,
            shape = shape,
            color = note.type.color.copy(alpha = 0.8f)
        ),
        shape = shape,
    ) {
        val textColor = LocalTextStyle.current.color.copy(alpha = 0.5f)
        Row(modifier = Modifier.fillMaxWidth()) {
            Column(
                modifier = Modifier.padding(
                    vertical = 12.dp,
                    horizontal = 18.dp
                ),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.Top
                ) {
                    Text(
                        text = note.type.title,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Medium,
                        color = textColor
                    )
                    Text(
                        text = note.timestamp.toLocalDateTime(TimeZone.currentSystemDefault())
                            .toString(),
                        fontSize = 12.sp,
                    )
                }
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = note.title,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                )

                note.content.forEach { line ->
                    Text(text = line, fontSize = 14.sp, color = textColor)
                }

                if (note.tags.isNotEmpty()) {
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        note.tags.forEach { tag ->
                            DailyNoteTag(tag)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun DailyNoteTag(tag: Tag) {
    Surface(
        shape = CircleShape,
        color = Color(0xFFEEF2FF),
        border = BorderStroke(1.dp, Color(0xFF6366F1))
    ) {
        Text(
            text = tag.name,
            color = Color(0xFF6366F1),
            fontSize = 12.sp,
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp)
        )
    }
}

@Composable
fun ReadStatusBadge(status: String) {
    Surface(
        shape = CircleShape,
        color = Color(0xFFFEF3C7)
    ) {
        Text(
            text = status,
            color = Color(0xFF92400E),
            fontSize = 11.sp,
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 3.dp)
        )
    }
}

@Preview
@Composable
private fun DailyNotesScreenPreview() {
    val sampleDailyNotes = listOf(
        DailyNote(
            id = Uuid.random(),
            type = DailyNoteType.CODE_REVIEW,
            title = "Reviewed PR #1234",
            content = listOf("✓ Learned about Flow operators", "✓ State hoisting pattern"),
            tags = listOf(Tag("kotlin"), Tag("flow")),
            timestamp = now()
        ),
        DailyNote(
            id = Uuid.random(),
            type = DailyNoteType.ARTICLE,
            title = "Compose Performance Tips",
            content = emptyList(),
            tags = emptyList(),
            timestamp = now().minus(6.days)
        ),
        DailyNote(
            id = Uuid.random(),
            type = DailyNoteType.QUICK_NOTE,
            title = "Debug CoroutineWorker GC issue",
            content = listOf("Check Flow observation in database context"),
            tags = emptyList(),
            timestamp = now().minus(3.days)
                .minus(4.hours)
        )
    )
    CaptureLogTheme {
        DailyNoteList(sampleDailyNotes)
    }
}
