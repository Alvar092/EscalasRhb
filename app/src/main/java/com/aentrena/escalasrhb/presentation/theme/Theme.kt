package com.aentrena.escalasrhb.presentation.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme = darkColorScheme(
    primary = P1D,
    secondary = Sec,
    tertiary = TextOnPrimD,
    background = backgD,
    surface = backgD,
    onPrimary = TextOnPrimD,
    onSecondary = TextOnPrimD,
    onBackground = TextOnPrimD,
    onSurface = TextOnPrimD

)

private val LightColorScheme = lightColorScheme(
    primary = P1L,
    secondary = Sec,
    tertiary = TextOnPrim,
    background = backg,
    surface = backg,
    onPrimary = TextOnPrim,
    onSecondary = TextOnPrim,
    onBackground = TextPrim,
    onSurface = TextPrim

)

@Composable
fun EscalasRhbTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = AppTypography,
        content = content
    )
}