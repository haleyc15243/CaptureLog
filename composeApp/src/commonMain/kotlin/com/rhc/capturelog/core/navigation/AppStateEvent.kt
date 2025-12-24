package com.rhc.capturelog.core.navigation

interface AppStateEvent

sealed interface TopBarEvent : AppStateEvent {
    data object TrashClicked : TopBarEvent
    data object ProfileClicked : TopBarEvent
    data object SearchClicked : TopBarEvent
}

data object FABClicked : AppStateEvent