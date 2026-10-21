package com.example.expensetracker.data

data class ExchangeRateResponse(
    val amount: Double,
    val base: String,
    val date: String,
    //A Map captures any key-value set. Gson handles the mapping automatically — no extra code needed.
    val rates: Map<String, Double>
)