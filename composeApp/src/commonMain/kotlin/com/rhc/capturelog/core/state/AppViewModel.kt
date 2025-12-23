package com.rhc.capturelog.core.state

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rhc.capturelog.core.navigation.AppStateEvent
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
class AppViewModel(private val savedStateHandle: SavedStateHandle) : ViewModel() {

    companion object {
        private const val KEY_TITLE = "title"
        private const val KEY_SUBTITLE = "subtitle"
        private const val KEY_SHOW_SEARCH = "show_search"
        private const val KEY_FAB_VISIBLE = "fab_visible"
    }

    private val titleFlow = savedStateHandle.getStateFlow(KEY_TITLE, "")
    private val subtitleFlow = savedStateHandle.getStateFlow(KEY_SUBTITLE, "")
    private val showSearchFlow = savedStateHandle.getStateFlow(KEY_SHOW_SEARCH, true)
    private val fabVisibleFlow = savedStateHandle.getStateFlow(KEY_FAB_VISIBLE, true)

    val uiState = combine(
        titleFlow,
        subtitleFlow,
        showSearchFlow,
        fabVisibleFlow
    ) { title, subtitle, showSearch, fabVisible ->
        AppState(
            topAppBarState = TopAppBarState(
                title = title.ifEmpty { null },
                subtitle = subtitle.ifEmpty { null },
                showSearchTopBarIcon = showSearch
            ),
            isFabVisible = fabVisible
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.Eagerly,
        initialValue = AppState()
    )

    private val _topBarEvents = MutableSharedFlow<AppStateEvent>()
    val appStateEvents = _topBarEvents.asSharedFlow()

    fun updateState(block: AppState.() -> AppState) {
        val currentState = uiState.value
        val newState = currentState.block()

        savedStateHandle[KEY_TITLE] = newState.topAppBarState.title ?: ""
        savedStateHandle[KEY_SUBTITLE] = newState.topAppBarState.subtitle ?: ""
        savedStateHandle[KEY_SHOW_SEARCH] = newState.topAppBarState.showSearchTopBarIcon
        savedStateHandle[KEY_FAB_VISIBLE] = newState.isFabVisible
    }

    fun setTitle(title: String, subtitle: String? = null) {
        savedStateHandle[KEY_TITLE] = title
        savedStateHandle[KEY_SUBTITLE] = subtitle ?: ""
    }
    
    fun showFab(isVisible: Boolean, action: (() -> Unit)? = null) {
        savedStateHandle[KEY_FAB_VISIBLE] = isVisible
    }
    
    fun setTopBarActions(actions: (@androidx.compose.runtime.Composable () -> Unit)?) {
         // Actions are not persistent state
    }

    fun emitAppStateEvent(event: AppStateEvent) {
        viewModelScope.launch {
            _topBarEvents.emit(event)
        }
    }
}
