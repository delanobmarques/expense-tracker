package com.example.expensetracker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.*
import com.example.expensetracker.ui.screens.AddExpenseScreen
import com.example.expensetracker.ui.screens.ExpenseListScreen
import com.example.expensetracker.ui.theme.ExpenseTrackerTheme
import com.example.expensetracker.model.Expense // Add this line
import com.example.expensetracker.model.sampleExpenses // Add this line
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.expensetracker.viewmodel.ExpenseViewModel


class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()  // draws content behind system bars (status bar, nav bar)

        //Replace setContent
        setContent {
            ExpenseTrackerTheme {
                var showAddScreen by remember { mutableStateOf(false) }
                var expenses by remember { mutableStateOf(sampleExpenses) }
                val expenseViewModel: ExpenseViewModel = viewModel()
                if (showAddScreen) {
                    AddExpenseScreen(
                        viewModel   = expenseViewModel,
                        onBackClick = { showAddScreen = false }
                    )
                } else {
                    ExpenseListScreen(
                        viewModel  = expenseViewModel,
                        onAddClick = { showAddScreen = true }
                    )
                }

            }
        }
    }
}
