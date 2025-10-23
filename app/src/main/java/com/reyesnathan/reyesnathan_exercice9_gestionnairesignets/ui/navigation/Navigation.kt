// Code généré par OpenAI. (2025). ChatGPT (GPT-5 Thinking, 2025-10-21) [Modèle de langage massif]. https://chat.openai.com
package com.reyesnathan.reyesnathan_exercice9_gestionnairesignets.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.reyesnathan.reyesnathan_exercice9_gestionnairesignets.ui.SignetViewModel
import com.reyesnathan.reyesnathan_exercice9_gestionnairesignets.ui.ecrans.EcranAccueil
import com.reyesnathan.reyesnathan_exercice9_gestionnairesignets.ui.ecrans.EcranAjouter
import com.reyesnathan.reyesnathan_exercice9_gestionnairesignets.ui.ecrans.EcranModifier
import com.reyesnathan.reyesnathan_exercice9_gestionnairesignets.ui.ecrans.EcranSignets

/**
 * Graphique de navigation principal.
 *
 * Source : Fiches 63.1 – La navigation et 63.2 – Le ViewModel et la navigation.
 *
 * Les routes disponibles :
 *  - "accueil"  : page d'accueil (temporaire ou image)
 *  - "liste"    : liste principale des signets
 *  - "ajouter"  : ajout d’un nouveau signet
 *  - "modifier/{id}" : modification d’un signet existant
 *  - "apropos"  : écran secondaire d’information
 */
@Composable
fun NavigationGraph(navController: NavHostController, viewModel: SignetViewModel) {

    NavHost(
        navController = navController,
        startDestination = "liste" // L’écran principal au démarrage
    ) {
        // 🏠 Écran d’accueil
        composable("accueil") {
            EcranAccueil()
        }

        // 📋 Liste de signets
        composable("liste") {
            // ⚙️ On passe le navController à EcranSignets pour activer "Modifier"
            EcranSignets(viewModel = viewModel, navController = navController)
        }

        // ➕ Ajout d’un signet
        composable("ajouter") {
            // ⚙️ On passe le navController pour redirection après ajout
            EcranAjouter(viewModel = viewModel, navController = navController)
        }

        // ✏️ Modification d’un signet (route dynamique)
        composable("modifier/{id}") { backStackEntry ->
            val id = backStackEntry.arguments?.getString("id")?.toIntOrNull()
            id?.let {
                EcranModifier(
                    signetId = it,
                    viewModel = viewModel,
                    navController = navController
                )
            }
        }

        // ℹ️ Page “À propos” (temporaire)
        composable("apropos") {
            EcranAccueil()
        }
    }
}
