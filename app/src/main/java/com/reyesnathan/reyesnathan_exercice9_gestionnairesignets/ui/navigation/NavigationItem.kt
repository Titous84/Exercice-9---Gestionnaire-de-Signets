package com.reyesnathan.reyesnathan_exercice9_gestionnairesignets.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Bookmarks
import androidx.compose.material.icons.filled.Home
import androidx.compose.ui.graphics.vector.ImageVector

/**
 * Représente chaque destination de la barre de navigation.
 * Source : Fiches 63.1 et 63.4 – Navigation et NavigationBar.
 */
sealed class NavigationItem(
    val route: String,
    val label: String,
    val icon: ImageVector
) {
    object Accueil : NavigationItem("accueil", "Accueil", Icons.Filled.Home)
    object Signets : NavigationItem("signets", "Signets", Icons.Filled.Bookmarks)
    object Ajouter : NavigationItem("ajouter", "Ajouter", Icons.Filled.Add)
}
