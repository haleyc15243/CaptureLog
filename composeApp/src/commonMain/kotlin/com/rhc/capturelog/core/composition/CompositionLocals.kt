package com.rhc.capturelog.core.composition

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.lifecycle.LifecycleOwner

val LocalRootLifecycleOwner = staticCompositionLocalOf<LifecycleOwner> {
    error("CompositionLocal LocalRootLifecycleOwner not present")
}
