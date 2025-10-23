package com.reyesnathan.reyesnathan_exercice9_gestionnairesignets.ui.navigation

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState

/**
 * Barre de navigation inférieure (Material 3).
 *
 * Source : Fiche 63.4 – NavigationBar.
 */
@Composable
fun BarreNavigation(navController: NavController) {
    val items = listOf(
        NavigationItem.Accueil,
        NavigationItem.Signets,
        NavigationItem.Ajouter
    )

    NavigationBar(tonalElevation = 3.dp) {
        val backStackEntry by navController.currentBackStackEntryAsState()
        val currentDestination = backStackEntry?.destination?.route

        items.forEach { item ->
            NavigationBarItem(
                selected = currentDestination == item.route,
                onClick = {
                    if (currentDestination != item.route)
                        navController.navigate(item.route) {
                            popUpTo(navController.graph.startDestinationId) { saveState = true }
                            launchSingleTop = true
                            restoreState = true
                        }
                },
                icon = {
                    Icon(
                        imageVector = item.icon,
                        contentDescription = item.label
                    )
                },
                label = { Text(item.label) },
                alwaysShowLabel = true
            )
        }
    }
}
