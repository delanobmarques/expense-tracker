package com.example.expensetracker.ui.rates
//everything that belongs to the Rates feature: the ViewModel, the screen composable, any state classes

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.expensetracker.network.RetrofitInstance
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.io.IOException


// Sealed Class: the compiler forces you to handle every case. Each state can carry different data.
sealed class RatesUiState {
    object Loading : RatesUiState()
    data class Success(
        val rates: Map<String, Double>,
        val currencies: Map<String, String>
    ) : RatesUiState()
    data class Error(val message: String) : RatesUiState()
}

class CurrencyRatesViewModel : ViewModel() {
    //_uiState: (mutable, private) is what we write to.
    private val _uiState = MutableStateFlow<RatesUiState>(RatesUiState.Loading)
    //uiState (read-only StateFlow) is what the UI observes.
    val uiState: StateFlow<RatesUiState> = _uiState.asStateFlow()

    fun loadRates() {
        _uiState.value = RatesUiState.Loading
        viewModelScope.launch {
            try {
                coroutineScope {
                    // Fire both calls at the same time
                    val ratesDeferred = async { RetrofitInstance.api.getLatestRates() }
                    val currenciesDeferred = async { RetrofitInstance.api.getCurrencies() }

                    // Wait for both — total time = slower of the two
                    val rates = ratesDeferred.await()
                    val currencies = currenciesDeferred.await()

                    _uiState.value = RatesUiState.Success(
                        rates = rates.rates,
                        currencies = currencies
                    )
                }
            } catch (e: IOException) {
                Log.e("CurrencyRatesVM", "Network error loading rates", e)
                _uiState.value = RatesUiState.Error("No internet connection (${e.localizedMessage ?: "Network error"})")
            } catch (e: Exception) {
                Log.e("CurrencyRatesVM", "Unexpected error loading rates", e)
                _uiState.value = RatesUiState.Error("Could not load rates: ${e.localizedMessage ?: "Unknown error"}")
            }
        }
    }

    init {
        //The init block runs when the ViewModel is first created
        // so the screen starts fetching data immediately without the composable having to trigger it manually.
        loadRates()
    }
}

