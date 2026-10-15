package com.example.expensetracker.ui.screens

import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.expensetracker.viewmodel.ExpenseViewModel
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.foundation.layout.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExpenseDetailScreen(
    expenseId:   Int,
    viewModel: ExpenseViewModel,
    onBackClick: () -> Unit = {},
    onEditClick: () -> Unit = {}

) {
    val expenses by viewModel.expenses.collectAsStateWithLifecycle()
    val expense = expenses.find { it.id == expenseId }

    if (expense == null) {//safety guard
        onBackClick() // if the expense was deleted while we were on this screen, we go back rather than crash
        return
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Expense Detail") },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                },
                actions = {
                    IconButton(onClick = onEditClick) {
                        Icon(Icons.Default.Edit, contentDescription = "Edit")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(text = expense.description, style = MaterialTheme.typography.headlineSmall)
            Text(text = "Category: ${expense.category}")
            Text(text = "Amount: $${expense.amount}")
            Text(text = "Date: ${expense.date}")
            if (expense.notes.isNotBlank()) {
                Text(text = "Notes: ${expense.notes}")
            }
        }
    }


}