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
import com.example.expensetracker.viewmodel.ExpenseViewModel

val expenseCategories = listOf("Food", "Transport", "Entertainment", "Other")

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddExpenseScreen(
    viewModel: ExpenseViewModel,
    onBackClick: () -> Unit = {}

) {
    var description by remember { mutableStateOf("") }
    var amount      by remember { mutableStateOf("") }
    var date        by remember { mutableStateOf("") }
    var notes       by remember { mutableStateOf("") }   // ← add this

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

            OutlinedTextField(
                value         = date,
                onValueChange = { date = it },
                label         = { Text("Date") },
                placeholder   = { Text("YYYY-MM-DD") },
                modifier      = Modifier.fillMaxWidth(),
                singleLine    = true
            )

            // Add this - Notes text field
            OutlinedTextField(
                value         = notes,
                onValueChange = { notes = it }, //onValueChange = { newValue -> notes = newValue }
                label         = { Text("Notes (Optional)") },
                placeholder   = { Text("Add any extra notes...") },
                modifier      = Modifier.fillMaxWidth(),
                singleLine    = true
            )

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

            val isFormValid = description.isNotBlank()
                    && amount.toDoubleOrNull() != null
                    && date.isNotBlank()

            Button(
                //Add notes to onClick
                onClick = {
                    viewModel.addExpense(
                        description = description,
                        amount      = amount.toDouble(),
                        category    = selectedCategory,
                        date        = date
                    )
                    onBackClick()   // go back to the list
                },
                enabled  = isFormValid,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp)
            ) {
                Text("Save Expense", style = MaterialTheme.typography.labelLarge)
            }
        }
    }
}

@Preview(showBackground = true, name = "Add Expense Screen")
@Composable
fun AddExpenseScreenPreview() {
    ExpenseTrackerTheme {
        /*simplified view*/
    }
}
