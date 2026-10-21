package com.example.expensetracker.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.expensetracker.ui.rates.CurrencyRatesViewModel
import com.example.expensetracker.ui.rates.RatesScreen
import com.example.expensetracker.ui.screens.AddExpenseScreen
import com.example.expensetracker.ui.screens.ExpenseDetailScreen
//import com.example.expensetracker.ui.screens.EditExpenseScreen
//import com.example.expensetracker.ui.screens.ExpenseDetailScreen
import com.example.expensetracker.ui.screens.ExpenseListScreen
import com.example.expensetracker.viewmodel.ExpenseViewModel

object Routes {
    const val LIST   = "list"
    const val ADD    = "add"
    const val DETAIL = "detail/{expenseId}"

    const val RATES = "rates"//**wire the navigation route
    fun detail(id: Int) = "detail/$id"
}

@Composable
fun AppNavigation(viewModel: ExpenseViewModel) {
    val navController = rememberNavController()

    NavHost(
        navController    = navController,
        startDestination = Routes.LIST
    ) {
        composable(Routes.LIST) {
            ExpenseListScreen(
                viewModel   = viewModel,
                onAddClick  = { navController.navigate(Routes.ADD) },
                onItemClick = { expense -> navController.navigate(Routes.detail(expense.id)) },
                //***update: add rates navigation
                onRatesClick = { navController.navigate(Routes.RATES) }
            )
        }
        composable(Routes.ADD) {
            AddExpenseScreen(
                viewModel   = viewModel,
                onBackClick = { navController.popBackStack() }
            )
        }
        composable(
            route     = Routes.DETAIL,
            arguments = listOf(navArgument("expenseId") { type = NavType.IntType })
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getInt("expenseId") ?: return@composable
            ExpenseDetailScreen(
                expenseId   = id,
                viewModel   = viewModel,
                onBackClick = { navController.popBackStack() },
                onEditClick = { /* TODO */ }
            )
        }
        //**register the RATES navigation route
        composable(Routes.RATES) {
            val ratesViewModel: CurrencyRatesViewModel = viewModel()
            RatesScreen(
                viewModel   = ratesViewModel,
                expenses     = viewModel.expenses.collectAsStateWithLifecycle().value,
                onBackClick = { navController.popBackStack() }
            )
        }
    }
}