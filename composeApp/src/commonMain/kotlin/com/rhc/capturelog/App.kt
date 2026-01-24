package com.rhc.capturelog

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.navigation.compose.rememberNavController
import com.rhc.capturelog.core.composition.LocalRootLifecycleOwner
import com.rhc.capturelog.core.navigation.CaptureLogNavigation
import com.rhc.capturelog.core.navigation.CaptureLogScaffold
import com.rhc.capturelog.core.state.AppViewModel
import com.rhc.capturelog.ui.theme.CaptureLogTheme
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun App() {
    CaptureLogTheme {
        val rootLifecycleOwner = LocalLifecycleOwner.current
        CompositionLocalProvider(
            LocalRootLifecycleOwner provides rootLifecycleOwner
        ) {
            val appViewModel: AppViewModel = koinViewModel()
            val navController = rememberNavController()
            CaptureLogScaffold(navController, appViewModel) { paddingValues ->
                CaptureLogNavigation(paddingValues, appViewModel, navController)
            }
        }
    }
}
