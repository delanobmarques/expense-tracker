package com.example.expensetracker.viewmodel

import androidx.lifecycle.ViewModel
import com.example.expensetracker.model.Expense
import com.example.expensetracker.model.sampleExpenses
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class ExpenseViewModel : ViewModel() {

    // Private mutable — only this class can change the list
    private val _expenses = MutableStateFlow(sampleExpenses)

    // Public read-only — screens observe this
    val expenses: StateFlow<List<Expense>> = _expenses.asStateFlow()

    fun addExpense(
        description: String,
        amount: Double,
        category: String,
        date: String,
        notes: String = ""
    ) {
        val newExpense = Expense(
            id          = (_expenses.value.maxOfOrNull { it.id } ?: 0) + 1,
            description = description,
            amount      = amount,
            category    = category,
            date        = date,
            notes       = notes
        )
        _expenses.update { current -> current + newExpense }
    }

    fun deleteExpense(id: Int) {
        _expenses.update { current -> current.filter { it.id != id } }
    }
}
