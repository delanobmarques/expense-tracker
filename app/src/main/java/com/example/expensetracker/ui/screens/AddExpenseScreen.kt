package com.example.expensetracker.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.expensetracker.ui.theme.ExpenseTrackerTheme

// ─────────────────────────────────────────────────────────────────────────────
// STEP 9 — Add Expense Screen (static form, Week 3)
//
// This screen collects user input but doesn't save anything yet.
// The "Save" button is disabled (Week 4 wires it to ViewModel).
// Navigation back uses onBackClick — an empty lambda for now (Week 5).
// ─────────────────────────────────────────────────────────────────────────────

// The four fixed categories — Week 6 loads these from JSON instead
val expenseCategories = listOf("Food", "Transport", "Entertainment", "Other")

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddExpenseScreen(
    onBackClick: () -> Unit = {},   // Week 5: navController.popBackStack()
    onSaveClick: (description: String, amount: String, category: String, date: String) -> Unit = { _, _, _, _ -> }
) {
    // INSTRUCTOR NOTE ─────────────────────────────────────────────────────────
    // remember { mutableStateOf("") } is local UI state — it lives only as long
    // as this composable is in the composition. When the user rotates the device
    // this state is lost. In Week 4 we move state up to ViewModel so it survives
    // rotation. This is the problem we're deliberately setting up to fix.
    // ─────────────────────────────────────────────────────────────────────────
    var description by remember { mutableStateOf("") }
    var amount      by remember { mutableStateOf("") }
    var date        by remember { mutableStateOf("") }

    // Dropdown state
    var selectedCategory  by remember { mutableStateOf(expenseCategories.first()) }
    var dropdownExpanded  by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Add Expense") },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector        = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor    = MaterialTheme.colorScheme.primary,
                    titleContentColor = Color.White,
                    navigationIconContentColor = Color.White
                )
            )
        }
    ) { innerPadding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 20.dp, vertical = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            // STEP 9a — Description field
            OutlinedTextField(
                value         = description,
                onValueChange = { description = it },
                label         = { Text("Description") },
                placeholder   = { Text("e.g. Groceries, Bus fare…") },
                modifier      = Modifier.fillMaxWidth(),
                singleLine    = true
            )

            // STEP 9b — Amount field (numeric keyboard)
            // INSTRUCTOR NOTE ─────────────────────────────────────────────────
            // KeyboardType.Decimal shows the numeric keyboard on mobile.
            // We validate the amount in Week 4 when we add the save logic.
            // For now an empty or invalid amount won't crash — Save is disabled.
            // ─────────────────────────────────────────────────────────────────
            OutlinedTextField(
                value         = amount,
                onValueChange = { amount = it },
                label         = { Text("Amount ($)") },
                placeholder   = { Text("0.00") },
                modifier      = Modifier.fillMaxWidth(),
                singleLine    = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                leadingIcon   = { Text("$", modifier = Modifier.padding(start = 12.dp)) }
            )

            // STEP 9c — Date field (plain text for now)
            // In a production app we'd use DatePickerDialog.
            // Keeping it simple here so it doesn't overshadow the Compose concepts.
            OutlinedTextField(
                value         = date,
                onValueChange = { date = it },
                label         = { Text("Date") },
                placeholder   = { Text("YYYY-MM-DD") },
                modifier      = Modifier.fillMaxWidth(),
                singleLine    = true
            )

            // STEP 9d — Category dropdown
            // ExposedDropdownMenuBox is the Material 3 pattern for a dropdown.
            // INSTRUCTOR NOTE ─────────────────────────────────────────────────
            // Ask students: "Why not use a Spinner like in XML layouts?"
            // Answer: Compose has no Spinner — ExposedDropdownMenuBox is the
            // direct equivalent. In Week 6 the items come from JSON, not a
            // hardcoded list.
            // ─────────────────────────────────────────────────────────────────
            ExposedDropdownMenuBox(
                expanded      = dropdownExpanded,
                onExpandedChange = { dropdownExpanded = !dropdownExpanded }
            ) {
                OutlinedTextField(
                    value             = selectedCategory,
                    onValueChange     = {},
                    readOnly          = true,
                    label             = { Text("Category") },
                    trailingIcon      = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = dropdownExpanded) },
                    modifier          = Modifier
                        .fillMaxWidth()
                        .menuAnchor()
                )
                ExposedDropdownMenu(
                    expanded      = dropdownExpanded,
                    onDismissRequest = { dropdownExpanded = false }
                ) {
                    expenseCategories.forEach { cat ->
                        DropdownMenuItem(
                            text    = { Text(cat) },
                            onClick = {
                                selectedCategory = cat
                                dropdownExpanded = false
                            }
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            // STEP 9e — Save button
            // Disabled until all required fields are filled.
            // In Week 4 this calls viewModel.addExpense(...).
            val isFormValid = description.isNotBlank()
                    && amount.toDoubleOrNull() != null
                    && date.isNotBlank()

            Button(
                onClick  = { onSaveClick(description, amount, selectedCategory, date) },
                enabled  = isFormValid,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp)
            ) {
                Text("Save Expense", style = MaterialTheme.typography.labelLarge)
            }

            // INSTRUCTOR NOTE ─────────────────────────────────────────────────
            // The button is enabled only when isFormValid is true.
            // This is reactive: Compose re-evaluates isFormValid every time
            // description, amount, or date changes. No listeners needed.
            // ─────────────────────────────────────────────────────────────────
        }
    }
}

@Preview(showBackground = true, name = "Add Expense Screen")
@Composable
fun AddExpenseScreenPreview() {
    ExpenseTrackerTheme {
        AddExpenseScreen()
    }
}
