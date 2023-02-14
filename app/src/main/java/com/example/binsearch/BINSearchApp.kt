package com.example.binsearch

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.example.binsearch.ui.component.NavigationBarApp
import com.example.binsearch.ui.navigation.BINSearchNavGraph
import com.example.binsearch.ui.navigation.NavItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BINSearchApp(modifier: Modifier = Modifier) {

    val navController = rememberNavController()

    val binNavItems = listOf(NavItem.Search, NavItem.History)

    Scaffold(
        modifier = modifier,
        bottomBar = {
            NavigationBarApp(
                modifier = modifier,
                navController = navController,
                navItems = binNavItems
            )
        }
    ) { innerPadding ->
        Box(modifier = modifier.padding(innerPadding)) {
            BINSearchNavGraph(
                modifier = modifier,
                navController = navController
            )
        }
    }
}