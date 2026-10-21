package com.example.expensetracker

import org.junit.Test
import com.example.expensetracker.network.RetrofitInstance
import com.example.expensetracker.ui.rates.CurrencyRatesViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun testEndpoints() = runBlocking {
        println("--- Testing dev ---")
        try {
            val res1 = RetrofitInstance.api.getLatestRates("CAD")
            println("dev latest: $res1")
        } catch (e: Exception) {
            println("dev latest error: $e")
        }

        try {
            val res2 = RetrofitInstance.api.getCurrencies()
            println("dev currencies: $res2")
        } catch (e: Exception) {
            println("dev currencies error: $e")
        }
    }
}