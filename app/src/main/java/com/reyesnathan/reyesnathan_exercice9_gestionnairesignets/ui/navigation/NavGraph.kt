package com.reyesnathan.reyesnathan_exercice9_gestionnairesignets.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.reyesnathan.reyesnathan_exercice9_gestionnairesignets.ui.SignetViewModel
import com.reyesnathan.reyesnathan_exercice9_gestionnairesignets.ui.ecrans.*

/**
 * Graphique de navigation : définit les routes vers les trois écrans.
 *
 * Source : Fiche 63.1 – La navigation.
 */
@Composable
fun NavGraph(navController: NavHostController, viewModel: SignetViewModel) {
    NavHost(
        navController = navController,
        startDestination = NavigationItem.Accueil.route
    ) {
        composable(NavigationItem.Accueil.route) { EcranAccueil() }
        composable(NavigationItem.Signets.route) {
            EcranSignets(viewModel = viewModel, navController = navController)
        }
        composable(NavigationItem.Ajouter.route) {
            EcranAjouter(viewModel = viewModel, navController = navController)
        }
        composable("modifier/{id}") { backStackEntry ->
            val id = backStackEntry.arguments?.getString("id")?.toIntOrNull()
            id?.let {
                EcranModifier(signetId = it, viewModel = viewModel, navController = navController)
            }
        }
    }
}
