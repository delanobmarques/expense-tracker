package com.example.expensetracker.ui.rates

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import com.example.expensetracker.model.Expense

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RatesScreen(
    viewModel: CurrencyRatesViewModel,
    expenses: List<Expense>,
    onBackClick: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()
    var selectedCurrency by remember { mutableStateOf("CAD") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Currency Rates") },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, "Back")
                    }
                }
            )
        }
    ) { innerPadding ->
        when (uiState) {
            is RatesUiState.Loading -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }
            is RatesUiState.Success -> {
                val state = uiState as RatesUiState.Success
                Column(modifier = Modifier.padding(innerPadding)) {
                    CurrencyDropdown(
                        rates = state.rates,
                        currencies = state.currencies,
                        selectedCurrency = selectedCurrency,
                        onSelect = { selectedCurrency = it }
                    )
                    // ConvertedExpenseList goes here
                    ConvertedExpenseList(
                        expenses         = expenses,
                        selectedCurrency = selectedCurrency,
                        rate             = state.rates[selectedCurrency] ?: 1.0
                    )
                }
            }
            is RatesUiState.Error -> {
                val state = uiState as RatesUiState.Error
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.padding(16.dp)
                    ) {
                        Text("Error: ${state.message}")
                        Spacer(modifier = Modifier.padding(8.dp))
                        Button(onClick = { viewModel.loadRates() }) {
                            Text("Retry")
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CurrencyDropdown(
    rates: Map<String, Double>,
    currencies: Map<String, String>,
    selectedCurrency: String,
    onSelect: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded },
        modifier = Modifier.fillMaxWidth().padding(16.dp)
    ) {
        OutlinedTextField(
//            value = "$selectedCurrency — ${currencies[selectedCurrency] ?: ""} — ${"%.4f".format(rates[selectedCurrency] ?: 0.0)}",
          value = "${selectedCurrency} — ${currencies[selectedCurrency] ?: ""}",
            onValueChange = {},
            readOnly = true,
            label = { Text("Select Currency") },
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded) },
            modifier = Modifier.menuAnchor().fillMaxWidth()
        )
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            rates.keys.sorted().forEach { code ->
                DropdownMenuItem(
                    text = { Text("$code — ${currencies[code] ?: code} — ${"%.4f".format(rates[code] ?: 0.0)}") },
                    onClick = {
                        onSelect(code)
                        expanded = false
                    }
                )
            }
        }
    }
}

@Composable
fun ConvertedExpenseList(
    expenses: List<com.example.expensetracker.model.Expense>,
    selectedCurrency: String,
    rate: Double
) {
    LazyColumn(
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(expenses) { expense ->
            Row(
                modifier              = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(expense.description)
                Text("${"%.2f".format(expense.amount * rate)} $selectedCurrency")
            }
        }
    }
}