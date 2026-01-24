package com.rhc.capturelog.core.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.rhc.capturelog.core.state.AppViewModel
import com.rhc.capturelog.features.capture.DailyNoteScreen

@Composable
fun CaptureLogNavigation(
    paddingValues: PaddingValues,
    appViewModel: AppViewModel,
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        startDestination = Destination.Today
    ) {
        composable<Destination.Today> {
            DailyNoteScreen(appViewModel, paddingValues)
        }
        composable<Destination.Queue> {
            // QueueScreen
        }
        composable<Destination.Settings> {
            // SettingsScreen
        }
    }
}