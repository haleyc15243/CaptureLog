package com.rhc.capturelog.features.dailynotes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rhc.capturelog.data.DailyNote
import com.rhc.capturelog.data.DailyNoteRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.stateIn
import org.koin.core.annotation.Factory

@Factory
class DailyNoteViewModel(dailyNoteRepository: DailyNoteRepository) : ViewModel() {
    val dailyNotes: StateFlow<List<DailyNote>> =
        flowOf(dailyNoteRepository.getDailyNotes())
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())
}
