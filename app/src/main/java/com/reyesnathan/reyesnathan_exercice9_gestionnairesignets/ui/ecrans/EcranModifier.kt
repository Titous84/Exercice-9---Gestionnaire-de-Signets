package com.reyesnathan.reyesnathan_exercice9_gestionnairesignets.ui.ecrans

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.reyesnathan.reyesnathan_exercice9_gestionnairesignets.R
import com.reyesnathan.reyesnathan_exercice9_gestionnairesignets.ui.SignetEvenement
import com.reyesnathan.reyesnathan_exercice9_gestionnairesignets.ui.SignetViewModel
import com.reyesnathan.reyesnathan_exercice9_gestionnairesignets.ui.components.PageTitle
import com.reyesnathan.reyesnathan_exercice9_gestionnairesignets.ui.components.SignetForm
import kotlinx.coroutines.flow.collectLatest

/**
 * Écran permettant de modifier un signet existant.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EcranModifier(
    signetId: Int,
    viewModel: SignetViewModel,
    navController: NavController
) {
    val signet by viewModel.obtenirSignet(signetId).collectAsState(initial = null)

    LaunchedEffect(Unit) {
        viewModel.evenements.collectLatest { evenement ->
            if (evenement is SignetEvenement.MiseAJourReussie) {
                navController.popBackStack()
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    PageTitle(
                        title = stringResource(R.string.modifier_page_title),
                        subtitle = stringResource(R.string.modifier_page_subtitle)
                    )
                }
            )
        }
    ) { innerPadding ->
        val signetCourant = signet
        Box(
            modifier = Modifier
                .padding(innerPadding)
                .padding(horizontal = 24.dp, vertical = 16.dp),
            contentAlignment = Alignment.Center
        ) {
            if (signetCourant == null) {
                Text(text = stringResource(R.string.modifier_introuvable))
            } else {
                SignetForm(
                    initialTitre = signetCourant.titre,
                    initialUrl = signetCourant.url,
                    initialDescription = signetCourant.description,
                    submitLabel = stringResource(R.string.modifier_signet_bouton)
                ) { titre, url, description ->
                    viewModel.mettreAJourSignet(
                        signet = signetCourant,
                        titre = titre,
                        url = url,
                        description = description
                    )
                }
            }
        }
    }
}
