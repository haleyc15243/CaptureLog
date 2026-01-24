package com.rhc.capturelog.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

private val LightColorScheme = lightColorScheme(
    primary = IndigoLightMode,
    primaryContainer = IndigoSurfaceLight,
    onPrimaryContainer = IndigoLightMode,
    secondary = CaptureLogGrayTextSecondary,
    background = GrayBg,
    onSurface = GrayText,
    outline = GrayBorder
)

private val DarkColorScheme = darkColorScheme(
    primary = IndigoDarkMode,
    primaryContainer = DarkGraySurface,
    onPrimaryContainer = LightGrayText,
    secondary = LightGrayText,
    background = DarkGrayBg,
    surface = DarkGraySurface,
    onSurface = LightGrayText,
    outline = DarkGrayOutline
)

@Immutable
data class ExtendedColors(
    val emerald: Color,
    val amber: Color,
    val violet: Color
)

val LightExtendedColors = ExtendedColors(
    emerald = EmeraldLightMode,
    amber = AmberLightMode,
    violet = VioletLightMode
)

val DarkExtendedColors = ExtendedColors(
    emerald = EmeraldDarkMode,
    amber = AmberDarkMode,
    violet = VioletDarkMode
)

val LocalExtendedColors = staticCompositionLocalOf {
    LightExtendedColors
}

object CaptureLogExtendedTheme {
    val colors: ExtendedColors
        @Composable
        @ReadOnlyComposable
        get() = LocalExtendedColors.current
}

@Composable
fun CaptureLogTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme
    val extendedColors = if (darkTheme) DarkExtendedColors else LightExtendedColors
    val spacing = Spacing()

    CompositionLocalProvider(
        LocalExtendedColors provides extendedColors,
        LocalSpacing provides spacing
    ) {
        Surface {
            MaterialTheme(
                colorScheme = colorScheme,
                content = content
            )
        }
    }
}
