package com.rhc.capturelog

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.rhc.capturelog.core.navigation.CaptureLogNavigation
import com.rhc.capturelog.core.navigation.CaptureLogScaffold
import com.rhc.capturelog.core.navigation.Destination
import com.rhc.capturelog.features.dailynotes.DailyNotesScreen
import com.rhc.capturelog.ui.theme.CaptureLogTheme

@Composable
fun App() {
    CaptureLogTheme {
        val navController = rememberNavController()
        CaptureLogScaffold(navController) { paddingValues ->
            CaptureLogNavigation(paddingValues, navController)
        }
    }
}
