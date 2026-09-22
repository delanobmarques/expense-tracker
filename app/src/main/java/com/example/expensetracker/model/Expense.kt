package com.example.expensetracker.model

// STEP 3 — Data model
// A data class is the right choice here: Kotlin generates equals(), hashCode(),
// toString(), and copy() automatically. We'll add Room annotations to this same
// class in Week 8 — nothing changes structurally.

data class Expense(
    val id: Int,
    val description: String,
    val amount: Double,
    val category: String,
    val date: String
)

// INSTRUCTOR NOTE ─────────────────────────────────────────────────────────────
// Ask students: "Why data class instead of a regular class?"
// Expected answer: automatic structural equality — two Expense objects with the
// same field values are equal (==), which matters for Compose recomposition.
// ─────────────────────────────────────────────────────────────────────────────

// Hardcoded sample data — Week 3 only.
// We replace this with ViewModel state in Week 4 and Room in Week 8.
val sampleExpenses = listOf(
    Expense(1, "Groceries",        67.45,  "Food",          "2026-09-19"),
    Expense(2, "Bus pass",         98.00,  "Transport",     "2026-09-18"),
    Expense(3, "Netflix",          17.99,  "Entertainment", "2026-09-15"),
    Expense(4, "Coffee & snacks",  12.50,  "Food",          "2026-09-14"),
    Expense(5, "Parking meter",     4.00,  "Transport",     "2026-09-12"),
    Expense(6, "Notebook",          8.99,  "Other",         "2026-09-10")
)
