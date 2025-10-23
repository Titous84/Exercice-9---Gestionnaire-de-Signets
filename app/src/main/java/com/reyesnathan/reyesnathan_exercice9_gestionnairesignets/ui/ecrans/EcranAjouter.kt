package com.reyesnathan.reyesnathan_exercice9_gestionnairesignets.ui.ecrans

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.reyesnathan.reyesnathan_exercice9_gestionnairesignets.R
import com.reyesnathan.reyesnathan_exercice9_gestionnairesignets.ui.SignetEvenement
import com.reyesnathan.reyesnathan_exercice9_gestionnairesignets.ui.SignetViewModel
import com.reyesnathan.reyesnathan_exercice9_gestionnairesignets.ui.components.PageTitle
import com.reyesnathan.reyesnathan_exercice9_gestionnairesignets.ui.components.SignetForm
import com.reyesnathan.reyesnathan_exercice9_gestionnairesignets.ui.navigation.NavigationItem
import kotlinx.coroutines.flow.collectLatest

/**
 * Écran permettant d’ajouter un nouveau signet.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EcranAjouter(
    viewModel: SignetViewModel,
    navController: NavController
) {
    LaunchedEffect(Unit) {
        viewModel.evenements.collectLatest { evenement ->
            if (evenement is SignetEvenement.AjoutReussi) {
                navController.navigate(NavigationItem.Signets.route) {
                    popUpTo(navController.graph.startDestinationId) {
                        saveState = true
                    }
                    launchSingleTop = true
                    restoreState = true
                }
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    PageTitle(
                        title = stringResource(R.string.ajouter_page_title),
                        subtitle = stringResource(R.string.ajouter_page_subtitle)
                    )
                }
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .padding(innerPadding)
                .padding(horizontal = 24.dp, vertical = 16.dp)
        ) {
            SignetForm(
                initialTitre = "",
                initialUrl = "",
                initialDescription = "",
                submitLabel = stringResource(R.string.ajouter_signet_bouton)
            ) { titre, url, description ->
                viewModel.ajouterSignet(titre, url, description)
            }
        }
    }
}
