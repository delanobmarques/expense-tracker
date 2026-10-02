package com.example.expensetracker.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val LightColors = lightColorScheme(
    primary         = PrimaryBlue,
    onPrimary       = Color.White,
    primaryContainer = PrimaryBlueDark,
    secondary       = SecondaryTeal,
    surface         = SurfaceLight,
    onSurface       = OnSurfaceLight,
    error           = ErrorRed
)

@Composable
fun ExpenseTrackerTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = LightColors,
        content     = content
    )
}
