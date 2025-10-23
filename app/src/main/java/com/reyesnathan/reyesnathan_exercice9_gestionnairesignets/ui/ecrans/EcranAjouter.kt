/*
 Inspiré de : Fiches 66.1 à 67.2 (TextField, Validation, Enregistrement, LaunchedEffect)
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
 * Écran pour ajouter un signet.
 * Gère la saisie, la validation et l’enregistrement via le ViewModel.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EcranAjouter(
    viewModel: SignetViewModel,
    navController: NavController
) {
    var titre by remember { mutableStateOf("") }
    var url by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }

    var erreurTitre by remember { mutableStateOf(false) }
    var erreurUrl by remember { mutableStateOf(false) }

    var confirmationVisible by remember { mutableStateOf(false) }

    Scaffold(
        topBar = { TopAppBar(title = { Text("Ajouter un signet") }) }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(16.dp)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            OutlinedTextField(
                value = titre,
                onValueChange = { titre = it; erreurTitre = false },
                label = { Text("Titre") },
                isError = erreurTitre,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(Modifier.height(8.dp))

            OutlinedTextField(
                value = url,
                onValueChange = { url = it; erreurUrl = false },
                label = { Text("URL") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Uri),
                isError = erreurUrl,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(Modifier.height(8.dp))

            OutlinedTextField(
                value = description,
                onValueChange = { description = it },
                label = { Text("Description (optionnelle)") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(Modifier.height(16.dp))

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
                Text("Enregistrer")
            }

            if (erreurTitre || erreurUrl) {
                Text(
                    "Veuillez remplir les champs obligatoires.",
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }

            if (confirmationVisible) {
                Text(
                    "✅ Signet enregistré avec succès !",
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.padding(top = 12.dp)
                )

                // Redirige automatiquement vers la liste après 1,5 s
                LaunchedEffect(Unit) {
                    delay(1500)
                    confirmationVisible = false
                    navController.navigate("liste")
                }
            }
        }
    }
}
