package com.example.expensetracker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.*
import com.example.expensetracker.ui.screens.AddExpenseScreen
import com.example.expensetracker.ui.screens.ExpenseListScreen
import com.example.expensetracker.ui.theme.ExpenseTrackerTheme

// ─────────────────────────────────────────────────────────────────────────────
// STEP 1 — MainActivity
//
// ComponentActivity is the base class for Compose apps.
// setContent {} replaces the old setContentView(R.layout.activity_main).
// Everything inside is Compose — no XML layouts anywhere.
// ─────────────────────────────────────────────────────────────────────────────

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()  // draws content behind system bars (status bar, nav bar)

        setContent {
            ExpenseTrackerTheme {
                // STEP 2 — Temporary manual screen switch
                // We use a simple Boolean flag to swap between screens.
                // This is a placeholder — Week 5 replaces this with NavController.
                //
                // INSTRUCTOR NOTE ─────────────────────────────────────────────
                // Show students the problem with this approach:
                //   1. Back button doesn't work (pressing Back exits the app)
                //   2. Can't deep-link to a screen
                //   3. Doesn't scale past 2 screens
                // Then: "Week 5 fixes all three with one composable: NavHost."
                // ─────────────────────────────────────────────────────────────

                var showAddScreen by remember { mutableStateOf(false) }

                if (showAddScreen) {
                    AddExpenseScreen(
                        onBackClick = { showAddScreen = false }
                    )
                } else {
                    ExpenseListScreen(
                        onAddClick = { showAddScreen = true }
                    )
                }
            }
        }
    }
}
