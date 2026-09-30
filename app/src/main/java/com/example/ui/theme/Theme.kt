package com.example.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable

private val DetectiveColorScheme = darkColorScheme(
    primary = AntiqueGold,
    onPrimary = InkDark,
    primaryContainer = GoldDark,
    onPrimaryContainer = ParchmentLight,
    secondary = ParchmentBase,
    onSecondary = InkDark,
    secondaryContainer = CorkBoard,
    onSecondaryContainer = ParchmentLight,
    tertiary = CrimsonRed,
    onTertiary = ParchmentLight,
    background = WoodDark,
    onBackground = ParchmentLight,
    surface = WoodBoard,
    onSurface = ParchmentLight,
    surfaceVariant = CorkBoard,
    onSurfaceVariant = ParchmentDark,
    outline = CardBorder
)

@Composable
fun MyApplicationTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = DetectiveColorScheme,
        typography = Typography,
        content = content
    )
}
