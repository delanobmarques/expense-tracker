package com.example.expensetracker.network

import com.example.expensetracker.data.ExchangeRateResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface FrankfurterApi {

    @GET("v1/latest") // maps to the URL path — Retrofit adds it to the base URL.
    suspend fun getLatestRates(//means this can only be called from a coroutine — it won't block the UI thread.
        @Query("base") base: String = "CAD" // appends ?base=CAD to the URL at runtime.
    ): ExchangeRateResponse//returns this data class.

    @GET("v1/currencies")// maps to the URL path — Retrofit adds it to the base URL.
    suspend fun getCurrencies(): Map<String, String> //returns this data class.
}