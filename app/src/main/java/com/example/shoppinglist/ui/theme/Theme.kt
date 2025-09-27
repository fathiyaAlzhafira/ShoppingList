package com.example.shoppinglist.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

// Light color scheme
private val LightColors = lightColorScheme(
    primary = PurpleLight,
    onPrimary = PurpleOn,
    primaryContainer = PurplePrimaryContainer,
    onPrimaryContainer = PurpleOnPrimaryContainer,
    secondary = PurpleSecondary,
    secondaryContainer = PurpleSecondaryContainer,
    onSecondaryContainer = PurpleOnSecondaryContainer,
    background = PurpleBackground,
    onBackground = PurpleOnBackground,
    surface = PurpleSurface,
    onSurface = PurpleOnSurface,
)

// Dark color scheme
private val DarkColors = darkColorScheme(
    primary = PurpleDark,
    onPrimary = PurpleOn,
    primaryContainer = PurpleDarkPrimaryContainer,
    onPrimaryContainer = PurpleDarkOnPrimaryContainer,
    secondary = PurpleSecondary,
    secondaryContainer = PurpleDarkSecondaryContainer,
    onSecondaryContainer = PurpleDarkOnSecondaryContainer,
    background = PurpleDarkBackground,
    onBackground = PurpleDarkOnBackground,
    surface = PurpleSurfaceDark,
    onSurface = PurpleOnSurfaceDark,
)


@Composable
fun ShoppingListTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) DarkColors else LightColors

    MaterialTheme(
        colorScheme = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}
