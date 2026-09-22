package com.example.expensetracker.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

// STEP 5 — App theme
// lightColorScheme() creates a Material 3 color scheme from named tokens.
// Every composable inside ExpenseTrackerTheme can access colors via
// MaterialTheme.colorScheme.primary, .surface, etc.
// In Week 10 we add a darkColorScheme() and switch based on isSystemInDarkTheme().

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

// INSTRUCTOR NOTE ─────────────────────────────────────────────────────────────
// Show students where MaterialTheme.colorScheme.primary comes from:
// trace PrimaryBlue → LightColors → ExpenseTrackerTheme → MainActivity.
// Point out: changing ONE line in Color.kt re-colors the entire app.
// ─────────────────────────────────────────────────────────────────────────────
