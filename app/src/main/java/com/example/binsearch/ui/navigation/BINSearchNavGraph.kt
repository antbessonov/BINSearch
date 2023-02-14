package com.example.binsearch.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.binsearch.ui.screen.RequestHistoryScreen
import com.example.binsearch.ui.screen.SearchCardInfoScreen

@Composable
fun BINSearchNavGraph(
    modifier: Modifier,
    navController: NavHostController,
    startDestination: String = BINSearchDestination.SEARCH.route,
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {
        composable(route = BINSearchDestination.SEARCH.route) {
            SearchCardInfoScreen()
        }
        composable(route = BINSearchDestination.HISTORY.route) {
            RequestHistoryScreen()
        }
    }
}