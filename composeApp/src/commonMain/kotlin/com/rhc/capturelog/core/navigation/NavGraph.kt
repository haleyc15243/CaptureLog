package com.rhc.capturelog.core.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.rhc.capturelog.features.dailynotes.DailyNotesScreen

@Composable
fun CaptureLogNavigation(
    paddingValues: PaddingValues,
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        startDestination = Destination.Today
    ) {
        composable<Destination.Today> {
            DailyNotesScreen(paddingValues)
        }
        composable<Destination.Queue> {
            // QueueScreen
        }
        composable<Destination.Settings> {
            // SettingsScreen
        }
    }
}