package com.example.binsearch.ui.navigation

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.ManageSearch
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.binsearch.R

sealed class NavItem(
    @StringRes val title: Int,
    val icon: ImageVector,
    val navRoute: String
) {
    object Search : NavItem(
        title = R.string.search,
        icon = Icons.Filled.ManageSearch,
        navRoute = BINSearchDestination.SEARCH.route
    )

    object History : NavItem(
        title = R.string.history,
        icon = Icons.Filled.History,
        navRoute = BINSearchDestination.HISTORY.route
    )
}
