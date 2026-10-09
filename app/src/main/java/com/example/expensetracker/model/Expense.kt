package com.example.expensetracker.model

data class Expense(
    val id: Int,
    val description: String,
    val amount: Double,
    val category: String,
    val date: String,
    val notes: String = "" //** in-class exercise
)

val sampleExpenses = listOf(//** in-class exercise
    Expense(1, "Groceries", 155.0, "Food", "2026-06-01", "Superstore weekly run"),
    Expense(2, "Bus pass", 50.0, "Transport", "2026-06-11", "Monthly pass"),
    Expense(3, "Netflix", 20.0, "Entertainment", "2026-06-05"),
    Expense(4, "Laptop", 250.0, "Other", "2026-06-12", "Repairs"),
    Expense(5, "Groceries", 250.0, "Food", "2026-06-20"),
    Expense(6, "Groceries", 100.0, "Food", "2026-06-26"),
    Expense(7, "Electricity Bill", 105.5, "Utilities", "2026-06-15", "May power bill"),
    Expense(8, "Pharmacy", 5.0, "Healthcare", "2026-06-18"),
    Expense(9, "New Shoes", 75.0, "Shopping", "2026-06-22", "Sale item"),
    Expense(10, "Bus Ticket", 2.5, "Transport", "2026-06-11"),
)
