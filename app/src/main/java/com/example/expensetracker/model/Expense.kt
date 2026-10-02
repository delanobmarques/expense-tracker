package com.example.expensetracker.model

data class Expense(
    val id: Int,
    val description: String,
    val amount: Double,
    val category: String,
    val date: String,
    val notes: String = "",
)

val sampleExpenses = listOf(
    Expense(1, "Groceries",        67.45,  "Food",          "2026-09-19"),
    Expense(2, "Bus pass",         98.00,  "Transport",     "2026-09-18"),
    Expense(3, "Netflix",          17.99,  "Entertainment", "2026-09-15"),
    Expense(4, "Coffee & snacks",  12.50,  "Food",          "2026-09-14"),
    Expense(5, "Parking meter",     4.00,  "Transport",     "2026-09-12"),
    Expense(6, "Notebook",          8.99,  "Other",         "2026-09-10"),
    Expense(7, "Gym membership",   45.00,  "Health",        "2026-09-08", "Monthly subscription"),
    Expense(8, "Electricity bill", 85.20,  "Utilities",     "2026-09-05", "Split with roommate"),
    Expense(9, "Dinner with team", 34.50,  "Food",          "2026-09-03", "Reimbursable work expense"),
    Expense(10, "Bookstore",       22.99,  "Shopping",      "2026-09-01", "Kotlin programming book")
)
