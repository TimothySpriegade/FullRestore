package dev.timothyspriegade.fullrestore.core.designsystem

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val LightColorScheme = lightColorScheme(
    primary = VgcTeal,
    onPrimary = Color.White,
    primaryContainer = VgcMist,
    onPrimaryContainer = VgcInk,
    secondary = VgcCoral,
    onSecondary = Color.White,
    secondaryContainer = Color(0xFFFFD9D5),
    onSecondaryContainer = VgcInk,
    tertiary = VgcAmber,
    onTertiary = VgcInk,
    background = VgcCanvas,
    onBackground = VgcInk,
    surface = VgcCloud,
    onSurface = VgcInk,
    surfaceVariant = Color(0xFFF1E9DE),
    onSurfaceVariant = Color(0xFF3C332C),
    outline = VgcOutline,
    outlineVariant = Color(0xFFD4C8BC)
)

private val DarkColorScheme = darkColorScheme(
    primary = VgcTeal,
    onPrimary = Color.Black,
    primaryContainer = VgcTealDark,
    onPrimaryContainer = Color.White,
    secondary = VgcAmber,
    onSecondary = VgcNight,
    secondaryContainer = VgcNightVariant,
    onSecondaryContainer = Color(0xFFEADFCF),
    tertiary = VgcCoral,
    onTertiary = Color.White,
    background = VgcNight,
    onBackground = Color(0xFFF2EDE6),
    surface = VgcNightSurface,
    onSurface = Color(0xFFF2EDE6),
    surfaceVariant = VgcNightVariant,
    onSurfaceVariant = Color(0xFFD2C7BA),
    outline = VgcNightOutline,
    outlineVariant = Color(0xFF3D3A35)
)

@Composable
fun FullRestoreTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) DarkColorScheme else LightColorScheme
    MaterialTheme(
        colorScheme = colors,
        typography = VgcTypography,
        content = content
    )
}
