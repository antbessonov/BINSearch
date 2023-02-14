package com.example.binsearch.ui.component

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.binsearch.ui.navigation.NavItem

@Composable
fun NavigationBarApp(
    modifier: Modifier,
    navController: NavHostController,
    navItems: List<NavItem>
) {
    NavigationBar(
        modifier = modifier
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentDestination = navBackStackEntry?.destination

        navItems.forEach { item ->
            NavigationBarItem(
                icon = {
                    Icon(
                        imageVector = item.icon,
                        contentDescription = stringResource(id = item.title)
                    )
                },
                label = { Text(stringResource(id = item.title)) },
                selected = currentDestination?.hierarchy?.any { it.route == item.navRoute } == true,
                onClick = {
                    navController.navigate(item.navRoute) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                    }
                }
            )
        }
    }
}

@Preview
@Composable
private fun NavigationBarAppPreview() {

    val binNavItems = listOf(NavItem.Search, NavItem.History)

    NavigationBarApp(
        modifier = Modifier,
        navController = rememberNavController(),
        navItems = binNavItems
    )
}

