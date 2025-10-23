/* 
 Inspiré de : Fiche 66.2 – Validation ; Fiche 66.3 – Enregistrement ;
              Fiche 68.1 – Retrouver les données de l’enregistrement à modifier.
*/
package com.reyesnathan.reyesnathan_exercice9_gestionnairesignets.ui.ecrans

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.reyesnathan.reyesnathan_exercice9_gestionnairesignets.ui.SignetViewModel
import kotlinx.coroutines.delay

/**
 * Écran de modification d’un signet existant.
 *
 * - Préremplit les champs à partir du ViewModel.
 * - Valide les données avant enregistrement.
 * - Affiche un message de confirmation puis retourne à la liste.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EcranModifier(
    signetId: Int,
    viewModel: SignetViewModel,
    navController: NavController
) {
    // 🔎 Récupération du signet à modifier
    val signet = viewModel.listeSignets.collectAsState().value.find { it.id == signetId }

    var titre by remember { mutableStateOf(signet?.titre ?: "") }
    var url by remember { mutableStateOf(signet?.url ?: "") }
    var description by remember { mutableStateOf(signet?.description ?: "") }

    // États d’erreur
    var erreurTitre by remember { mutableStateOf(false) }
    var erreurUrl by remember { mutableStateOf(false) }

    // Confirmation de modification
    var confirmationVisible by remember { mutableStateOf(false) }

    Scaffold(
        topBar = { TopAppBar(title = { Text("Modifier le signet") }) }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(16.dp)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // --- Champ : Titre
            OutlinedTextField(
                value = titre,
                onValueChange = { titre = it; erreurTitre = false },
                label = { Text("Titre") },
                isError = erreurTitre,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(Modifier.height(8.dp))

            // --- Champ : URL
            OutlinedTextField(
                value = url,
                onValueChange = { url = it; erreurUrl = false },
                label = { Text("URL") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Uri),
                isError = erreurUrl,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(Modifier.height(8.dp))

            // --- Champ : Description
            OutlinedTextField(
                value = description,
                onValueChange = { description = it },
                label = { Text("Description (optionnelle)") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(Modifier.height(16.dp))

            // --- Bouton Enregistrer
            Button(
                onClick = {
                    erreurTitre = titre.isBlank()
                    erreurUrl = url.isBlank()

                    if (!erreurTitre && !erreurUrl) {
                        viewModel.ajouterSignet(titre, url, description)
                        confirmationVisible = true
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Enregistrer les modifications")
            }

            // --- Message d’erreur
            if (erreurTitre || erreurUrl) {
                Text(
                    text = "Veuillez remplir les champs obligatoires (titre et URL).",
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }

            // --- Message de confirmation + retour automatique
            if (confirmationVisible) {
                Text(
                    text = "✅ Modifications enregistrées avec succès !",
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.padding(top = 12.dp)
                )

                LaunchedEffect(Unit) {
                    delay(1500)
                    confirmationVisible = false
                    navController.navigate("liste")
                }
            }
        }
    }
}
