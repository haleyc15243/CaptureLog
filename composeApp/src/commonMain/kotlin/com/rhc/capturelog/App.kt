package com.rhc.capturelog

import androidx.compose.runtime.Composable
import com.rhc.capturelog.core.navigation.CaptureLogScaffold
import com.rhc.capturelog.ui.theme.CaptureLogTheme

@Composable
fun App() {
    CaptureLogTheme {
        CaptureLogScaffold()
    }
}
