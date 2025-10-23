package com.reyesnathan.reyesnathan_exercice9_gestionnairesignets.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Bookmarks
import androidx.compose.material.icons.filled.Home
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.annotation.StringRes
import com.reyesnathan.reyesnathan_exercice9_gestionnairesignets.R

/**
 * Représente chaque destination de la barre de navigation.
 * Source : Fiches 63.1 et 63.4 – Navigation et NavigationBar.
 */
sealed class NavigationItem(
    val route: String,
    @StringRes val labelRes: Int,
    val icon: ImageVector
) {
    object Accueil : NavigationItem("accueil", R.string.nav_accueil, Icons.Filled.Home)
    object Signets : NavigationItem("signets", R.string.nav_signets, Icons.Filled.Bookmarks)
    object Ajouter : NavigationItem("ajouter", R.string.nav_ajouter, Icons.Filled.Add)
}
