package com.rhc.capturelog.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

private val LightColorScheme = lightColorScheme(
    primary = CaptureLogIndigo,
    primaryContainer = CaptureLogIndigoLight,
    onPrimaryContainer = CaptureLogIndigo,
    secondary = CaptureLogGrayTextSecondary,
    background = CaptureLogGrayBg,
    onSurface = CaptureLogGrayText,
    outline = CaptureLogGrayBorder
)

private val DarkColorScheme = darkColorScheme(
    primary = CaptureLogIndigo,
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
    val codeReviewGreen: Color,
    val articleYellow: Color,
)

val LightExtendedColors = ExtendedColors(
    codeReviewGreen = CodeReviewGreenLightMode,
    articleYellow = ArticleYellowLightMode,
)

val DarkExtendedColors = ExtendedColors(
    codeReviewGreen = CodeReviewGreenDarkMode,
    articleYellow = ArticleYellowDarkMode,
)

val LocalExtendedColors = staticCompositionLocalOf {
    LightExtendedColors
}

object CaptureLogExtendedTheme {
    val colorScheme: ExtendedColors
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

    CompositionLocalProvider(
        LocalExtendedColors provides extendedColors
    ) {
        MaterialTheme(
            colorScheme = colorScheme,
            content = content
        )
    }
}
