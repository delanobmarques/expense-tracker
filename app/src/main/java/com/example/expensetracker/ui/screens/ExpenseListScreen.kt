package com.example.expensetracker.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.expensetracker.model.Expense
import com.example.expensetracker.model.sampleExpenses
import com.example.expensetracker.ui.theme.ColorEntertainment
import com.example.expensetracker.ui.theme.ColorFood
import com.example.expensetracker.ui.theme.ColorOther
import com.example.expensetracker.ui.theme.ColorTransport
import com.example.expensetracker.ui.theme.ExpenseTrackerTheme

// ─────────────────────────────────────────────────────────────────────────────
// STEP 6 — Expense List Screen
// This is the main screen. It uses Scaffold (the standard Material 3 page
// container) which gives us a TopAppBar slot and a FAB slot for free.
// ─────────────────────────────────────────────────────────────────────────────

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExpenseListScreen(
    expenses: List<Expense> = sampleExpenses,  // Week 4: replaced by ViewModel state
    onAddClick: () -> Unit = {}                // Week 5: navigates to AddExpenseScreen
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "My Expenses",
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor    = MaterialTheme.colorScheme.primary,
                    titleContentColor = Color.White
                )
            )
        },
        floatingActionButton = {
            // INSTRUCTOR NOTE ─────────────────────────────────────────────────
            // The FAB does nothing yet — onAddClick is an empty lambda.
            // In Week 5 we pass navController.navigate("add_expense") here.
            // Ask students: "Why pass the action as a parameter instead of
            // hardcoding it?" → separation of concerns; easier to test.
            // ─────────────────────────────────────────────────────────────────
            FloatingActionButton(
                onClick           = onAddClick,
                containerColor    = MaterialTheme.colorScheme.primary,
                contentColor      = Color.White
            ) {
                Icon(
                    imageVector         = Icons.Filled.Add,
                    contentDescription  = "Add expense"
                )
            }
        }
    ) { innerPadding ->

        // STEP 7 — Summary bar
        // Shows the running total across all expenses.
        // In Week 4 this value comes from ViewModel; for now we calculate inline.
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            SummaryBar(total = expenses.sumOf { it.amount })

            // STEP 8 — LazyColumn
            // LazyColumn only composes the items currently visible on screen.
            // For a short list this doesn't matter — but it's the right habit.
            // Compare to RecyclerView: LazyColumn is the Compose equivalent,
            // without the ViewHolder boilerplate.
            LazyColumn(
                contentPadding    = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(
                    items = expenses,
                    key   = { it.id }   // stable key helps Compose animate list changes
                ) { expense ->
                    ExpenseItem(expense = expense)
                }
            }
        }
    }
}

// ─────────────────────────────────────────────────────────────────────────────
// STEP 7 — Summary Bar composable
// ─────────────────────────────────────────────────────────────────────────────
@Composable
fun SummaryBar(total: Double) {
    Surface(
        color     = MaterialTheme.colorScheme.primaryContainer,
        tonalElevation = 2.dp
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 12.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment     = Alignment.CenterVertically
        ) {
            Text(
                text  = "Total spent",
                style = MaterialTheme.typography.labelLarge,
                color = Color.White.copy(alpha = 0.85f)
            )
            Text(
                text       = "$${"%.2f".format(total)}",
                style      = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color      = Color.White
            )
        }
    }
}

// ─────────────────────────────────────────────────────────────────────────────
// STEP 8 — Expense item card
// One Card per expense. Card gives us elevation + rounded corners.
// ─────────────────────────────────────────────────────────────────────────────
@Composable
fun ExpenseItem(expense: Expense) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape    = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment     = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            // Left side: category dot + text
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.weight(1f)
            ) {
                // INSTRUCTOR NOTE ─────────────────────────────────────────────
                // The colored dot is a Box with a CircleShape clip.
                // Ask students: "How would you replace this with a real icon?"
                // Hint: use an Icon composable inside the Box, or swap in
                // Icons.Filled.Restaurant for food, etc.
                // ─────────────────────────────────────────────────────────────
                Box(
                    modifier = Modifier
                        .size(12.dp)
                        .clip(CircleShape)
                        .background(categoryColor(expense.category))
                )
                Spacer(modifier = Modifier.width(12.dp))
                Column {
                    Text(
                        text       = expense.description,
                        style      = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.Medium
                    )
                    Text(
                        text  = "${expense.category}  ·  ${expense.date}",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                    )
                }
            }

            // Right side: amount
            Text(
                text       = "-$${"%.2f".format(expense.amount)}",
                style      = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Bold,
                color      = MaterialTheme.colorScheme.error
            )
        }
    }
}

// Helper: maps category string → accent color
// In Week 6 this comes from the JSON category config instead.
fun categoryColor(category: String): Color = when (category) {
    "Food"          -> ColorFood
    "Transport"     -> ColorTransport
    "Entertainment" -> ColorEntertainment
    else            -> ColorOther
}

// ─────────────────────────────────────────────────────────────────────────────
// Previews — run these in Android Studio with the Split view open
// ─────────────────────────────────────────────────────────────────────────────
@Preview(showBackground = true, name = "Expense List")
@Composable
fun ExpenseListScreenPreview() {
    ExpenseTrackerTheme {
        ExpenseListScreen()
    }
}

@Preview(showBackground = true, name = "Single Item")
@Composable
fun ExpenseItemPreview() {
    ExpenseTrackerTheme {
        ExpenseItem(expense = sampleExpenses.first())
    }
}
