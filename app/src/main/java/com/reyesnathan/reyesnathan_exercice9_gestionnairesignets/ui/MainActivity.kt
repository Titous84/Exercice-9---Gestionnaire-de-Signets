package com.reyesnathan.reyesnathan_exercice9_gestionnairesignets.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.reyesnathan.reyesnathan_exercice9_gestionnairesignets.data.SignetDatabase
import com.reyesnathan.reyesnathan_exercice9_gestionnairesignets.data.repository.SignetRepository
import com.reyesnathan.reyesnathan_exercice9_gestionnairesignets.ui.navigation.BarreNavigation
import com.reyesnathan.reyesnathan_exercice9_gestionnairesignets.ui.navigation.NavGraph
import com.reyesnathan.reyesnathan_exercice9_gestionnairesignets.ui.theme.ReyesNathan_Exercice9_GestionnaireSignetsTheme

/**
 * Activité principale : initialise la base, le ViewModel et la navigation.
 *
 * Sources : Fiches 63.1 → 63.4.
 */
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val base = SignetDatabase.Companion.obtenirInstance(applicationContext)
        val repository = SignetRepository(base.signetDao())
        val usine = SignetViewModelFactory(repository)

        setContent {
            ReyesNathan_Exercice9_GestionnaireSignetsTheme {
                val navController = rememberNavController()
                val viewModel: SignetViewModel = viewModel(factory = usine)

                Scaffold(
                    bottomBar = { BarreNavigation(navController) }
                ) { innerPadding ->
                    Surface(modifier = Modifier.Companion.padding(innerPadding)) {
                        NavGraph(navController = navController, viewModel = viewModel)
                    }
                }
            }
        }
    }
}