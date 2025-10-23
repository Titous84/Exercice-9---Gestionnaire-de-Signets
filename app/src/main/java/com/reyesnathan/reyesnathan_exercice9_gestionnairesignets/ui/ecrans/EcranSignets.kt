/*
 Inspiré de : Fiches 55.1 – LazyColumn ; 60.1 – Lien hypertexte ; 60.2 – Card ; 66.2 – Validation ; 68.1 – Modification
*/
package com.reyesnathan.reyesnathan_exercice9_gestionnairesignets.ui.ecrans

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.reyesnathan.reyesnathan_exercice9_gestionnairesignets.R
import com.reyesnathan.reyesnathan_exercice9_gestionnairesignets.data.entities.Signet
import com.reyesnathan.reyesnathan_exercice9_gestionnairesignets.ui.SignetViewModel
import java.text.DateFormat
import java.util.Date

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EcranSignets(viewModel: SignetViewModel, navController: NavController) {
    val listeSignets by viewModel.listeSignets.collectAsState()
    var signetASupprimer by remember { mutableStateOf<Signet?>(null) }

    var titre by remember { mutableStateOf("") }
    var url by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }

    Scaffold(
        topBar = { TopAppBar(title = { Text(stringResource(R.string.app_title)) }) }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(16.dp)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // --- Zone d'ajout rapide (inchangée)
            OutlinedTextField(
                value = titre,
                onValueChange = { titre = it },
                label = { Text(stringResource(R.string.label_titre)) },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(Modifier.height(8.dp))
            OutlinedTextField(
                value = url,
                onValueChange = { url = it },
                label = { Text(stringResource(R.string.label_url)) },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Uri),
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(Modifier.height(8.dp))
            OutlinedTextField(
                value = description,
                onValueChange = { description = it },
                label = { Text(stringResource(R.string.label_description)) },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(Modifier.height(16.dp))
            Button(
                onClick = {
                    if (titre.isNotBlank() && url.isNotBlank()) {
                        viewModel.ajouterSignet(titre, url, description)
                        titre = ""; url = ""; description = ""
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(stringResource(R.string.ajouter_signet))
            }
            Spacer(Modifier.height(24.dp))

            Text(stringResource(R.string.titre_liste), style = MaterialTheme.typography.titleMedium)
            Spacer(Modifier.height(8.dp))

            LazyColumn(modifier = Modifier.fillMaxSize()) {
                items(listeSignets) { signet ->
                    CarteSignet(
                        signet = signet,
                        onModifier = { navController.navigate("modifier/${signet.id}") },
                        onSupprimer = { signetASupprimer = signet }
                    )
                }
            }
        }
    }

    // --- Boîte de confirmation avant suppression
    signetASupprimer?.let { signet ->
        AlertDialog(
            onDismissRequest = { signetASupprimer = null },
            title = { Text("Confirmer la suppression") },
            text = { Text("Voulez-vous vraiment supprimer « ${signet.titre} » ?") },
            confirmButton = {
                TextButton(onClick = {
                    viewModel.supprimerSignet(signet)
                    signetASupprimer = null
                }) { Text("Supprimer", color = MaterialTheme.colorScheme.error) }
            },
            dismissButton = {
                TextButton(onClick = { signetASupprimer = null }) { Text("Annuler") }
            }
        )
    }
}

/**
 * Carte affichant un signet avec URL cliquable, date, bouton modifier/supprimer
 */
@Composable
fun CarteSignet(signet: Signet, onModifier: () -> Unit, onSupprimer: () -> Unit) {
    val contexte = LocalContext.current

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp),
        shape = MaterialTheme.shapes.medium,
        elevation = CardDefaults.cardElevation(defaultElevation = 3.dp)
    ) {
        Column(Modifier.padding(12.dp)) {
            Text(signet.titre, style = MaterialTheme.typography.titleMedium)

            // URL cliquable
            val urlTexte = buildAnnotatedString {
                withStyle(
                    SpanStyle(
                        color = MaterialTheme.colorScheme.primary,
                        textDecoration = TextDecoration.Underline
                    )
                ) { append(signet.url) }
            }
            Text(
                text = urlTexte,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.clickable {
                    ouvrirUrlDansNavigateur(contexte, signet.url)
                }
            )

            if (signet.description.isNotBlank()) {
                Spacer(Modifier.height(4.dp))
                Text(signet.description, style = MaterialTheme.typography.bodySmall)
            }

            Spacer(Modifier.height(4.dp))
            Text(
                text = stringResource(R.string.date_ajout, formatDateCourt(signet.dateAjout)),
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(Modifier.height(8.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                OutlinedButton(onClick = onModifier) { Text("Modifier") }
                Button(
                    onClick = onSupprimer,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.errorContainer
                    )
                ) {
                    Text(
                        stringResource(R.string.btn_supprimer),
                        color = MaterialTheme.colorScheme.onErrorContainer
                    )
                }
            }
        }
    }
}

private fun ouvrirUrlDansNavigateur(context: Context, url: String) {
    runCatching {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        context.startActivity(intent)
    }
}

private fun formatDateCourt(ms: Long): String =
    DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT).format(Date(ms))
