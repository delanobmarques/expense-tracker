package com.example.expensetracker.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
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
                onItemClick = { expense -> navController.navigate(Routes.detail(expense.id)) }
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
                onEditClick = { /* Class 2 */ }
            )
        }
    }
}