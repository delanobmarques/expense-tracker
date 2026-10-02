package com.example.expensetracker.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AddCircleOutline
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.expensetracker.model.Expense
import com.example.expensetracker.model.sampleExpenses
import com.example.expensetracker.ui.theme.ColorEntertainment
import com.example.expensetracker.ui.theme.ColorFood
import com.example.expensetracker.ui.theme.ColorOther
import com.example.expensetracker.ui.theme.ColorTransport
import com.example.expensetracker.ui.theme.ExpenseTrackerTheme
import com.example.expensetracker.viewmodel.ExpenseViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExpenseListScreen(
    viewModel: ExpenseViewModel = viewModel(),
    onAddClick: () -> Unit = {}
) {
    val expenses by viewModel.expenses.collectAsStateWithLifecycle()
    val total = expenses.sumOf { it.amount }

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
            FloatingActionButton(
                onClick        = onAddClick,
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor   = Color.White
            ) {
                Icon(
                    imageVector        = Icons.Filled.AddCircleOutline,
                    contentDescription = "Add expense"
                )
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            SummaryBar(count = expenses.size, total = total)

            LazyColumn(
                //fix: add bottom padding to LazyColumn so last item clears FAB
                contentPadding = PaddingValues(start = 16.dp, end = 16.dp, top = 8.dp, bottom = 80.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(expenses, key = { it.id }) { expense ->
                    ExpenseItem(
                        expense  = expense,
                        onDelete = { viewModel.deleteExpense(expense.id) }
                    )
                }
            }
        }
    }
}

// ── Summary bar — v1 style, v2 expense count added ───────────────────────
@Composable
fun SummaryBar(count: Int, total: Double) {
    Surface(
        color          = MaterialTheme.colorScheme.primaryContainer,
        tonalElevation = 2.dp
    ) {
        Row(
            modifier              = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 12.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment     = Alignment.CenterVertically
        ) {
            Text(
                text  = "$count expense${if (count == 1) "" else "s"}",
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

// ── Expense card — v1 style + delete button + notes ──────────────────────
@Composable
fun ExpenseItem(
    expense:  Expense,
    onDelete: () -> Unit = {}
) {
    Card(
        modifier  = Modifier.fillMaxWidth(),
        shape     = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier              = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment     = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            // Left: category dot + text
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier          = Modifier.weight(1f)
            ) {
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
                    if (expense.notes.isNotBlank()) {
                        Text(
                            text  = expense.notes,
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                        )
                    }
                }
            }

            // Right: amount + delete
            Column(horizontalAlignment = Alignment.End) {
                Text(
                    text       = "-$${"%.2f".format(expense.amount)}",
                    style      = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Bold,
                    color      = if (expense.amount < 10.0) Color(0xFF2E7D32) else MaterialTheme.colorScheme.error
                )
                IconButton(onClick = onDelete) {
                    Icon(
                        imageVector        = Icons.Filled.Delete,
                        contentDescription = "Delete",
                        tint               = MaterialTheme.colorScheme.error
                    )
                }
            }
        }
    }
}

fun categoryColor(category: String): Color = when (category) {
    "Food"          -> ColorFood
    "Transport"     -> ColorTransport
    "Entertainment" -> ColorEntertainment
    else            -> ColorOther
}

// ── Previews ──────────────────────────────────────────────────────────────
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