package com.rhc.capturelog.core.state

import androidx.compose.runtime.Composable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

// Represents the global UI state that can be modified by any screen.
data class AppUiState(
    val title: String = "",
    val subtitle: String = "",
    val isBottomBarVisible: Boolean = true,
    val isFabVisible: Boolean = true,
    // Non-persistent state. These are reset on process death.
    val fabAction: (() -> Unit)? = null,
    val topBarActions: @Composable (() -> Unit)? = null
)

class AppViewModel(private val savedStateHandle: SavedStateHandle) : ViewModel() {

    companion object {
        // Keys for SavedStateHandle
        private const val KEY_TITLE = "title"
        private const val KEY_SUBTITLE = "subtitle"
        private const val KEY_BOTTOM_BAR_VISIBLE = "bottomBarVisible"
        private const val KEY_FAB_VISIBLE = "fabVisible"
    }

    private val _uiState = MutableStateFlow(
        // Restore state from SavedStateHandle on init
        AppUiState(
            title = savedStateHandle.get<String>(KEY_TITLE) ?: "",
            subtitle = savedStateHandle.get<String>(KEY_SUBTITLE) ?: "",
            isBottomBarVisible = savedStateHandle.get<Boolean>(KEY_BOTTOM_BAR_VISIBLE) ?: true,
            isFabVisible = savedStateHandle.get<Boolean>(KEY_FAB_VISIBLE) ?: true
        )
    )
    val uiState = _uiState.asStateFlow()

    fun setTitle(title: String, subtitle: String = "") {
        savedStateHandle[KEY_TITLE] = title
        savedStateHandle[KEY_SUBTITLE] = subtitle
        _uiState.update { it.copy(title = title, subtitle = subtitle) }
    }

    fun showBottomBar(isVisible: Boolean) {
        savedStateHandle[KEY_BOTTOM_BAR_VISIBLE] = isVisible
        _uiState.update { it.copy(isBottomBarVisible = isVisible) }
    }

    fun showFab(isVisible: Boolean, action: (() -> Unit)? = null) {
        savedStateHandle[KEY_FAB_VISIBLE] = isVisible
        // Actions are not saved as they are transient UI concerns.
        _uiState.update { it.copy(isFabVisible = isVisible, fabAction = action) }
    }

    fun setTopBarActions(actions: (@Composable () -> Unit)?) {
        // Actions are not saved.
        _uiState.update { it.copy(topBarActions = actions) }
    }
}

// CompositionLocal to provide the AppViewModel throughout the app
val LocalAppViewModel = staticCompositionLocalOf<AppViewModel> {
    error("No AppViewModel provided. Make sure to wrap your app in a CompositionLocalProvider.")
}
