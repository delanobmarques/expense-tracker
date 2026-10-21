package com.example.expensetracker.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

//Singleton pattern (object): we want exactly one Retrofit instance shared by the whole app
// creating multiple instances wastes memory and can cause inconsistent behavior.
object RetrofitInstance {
    private val retrofit = Retrofit.Builder()
        .baseUrl("https://api.frankfurter.dev/") // trailing slash required - Retrofit appends the endpoint path and will produce the wrong URL without it.
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    val api: FrankfurterApi = retrofit.create(FrankfurterApi::class.java)
}