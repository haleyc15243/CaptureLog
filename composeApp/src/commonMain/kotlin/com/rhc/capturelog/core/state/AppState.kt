package com.rhc.capturelog.core.state

import kotlinx.serialization.Serializable

@Serializable
data class AppState(
    val topAppBarState: TopAppBarState = TopAppBarState(),
    val isFabVisible: Boolean = true,
    // Add more serializable properties to represent appbar state, etc.
)

@Serializable
data class TopAppBarState(
    val title: String? = null,
    val subtitle: String? = null,
    val showSearchTopBarIcon: Boolean = true
)