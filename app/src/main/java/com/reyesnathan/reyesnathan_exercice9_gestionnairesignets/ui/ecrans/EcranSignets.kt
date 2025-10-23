package com.reyesnathan.reyesnathan_exercice9_gestionnairesignets.ui.ecrans

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.reyesnathan.reyesnathan_exercice9_gestionnairesignets.R
import com.reyesnathan.reyesnathan_exercice9_gestionnairesignets.data.entities.Signet
import com.reyesnathan.reyesnathan_exercice9_gestionnairesignets.ui.SignetViewModel
import com.reyesnathan.reyesnathan_exercice9_gestionnairesignets.ui.components.PageTitle
import java.text.DateFormat
import java.util.Date

/**
 * Écran listant l’ensemble des signets enregistrés.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EcranSignets(viewModel: SignetViewModel, navController: NavController) {
    val listeSignets by viewModel.listeSignets.collectAsState()
    var signetASupprimer by remember { mutableStateOf<Signet?>(null) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    PageTitle(
                        title = stringResource(R.string.signets_page_title),
                        subtitle = stringResource(R.string.signets_page_subtitle)
                    )
                }
            )
        }
    ) { paddingValues ->
        if (listeSignets.isEmpty()) {
            ListeVide(modifier = Modifier.padding(paddingValues))
        } else {
            LazyColumn(
                modifier = Modifier
                    .padding(paddingValues)
                    .fillMaxSize(),
                contentPadding = PaddingValues(horizontal = 24.dp, vertical = 16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(listeSignets, key = { it.id }) { signet ->
                    CarteSignet(
                        signet = signet,
                        onModifier = {
                            navController.navigate("modifier/${signet.id}")
                        },
                        onSupprimer = { signetASupprimer = signet }
                    )
                }
            }
        }
    }

    signetASupprimer?.let { signet ->
        AlertDialog(
            onDismissRequest = { signetASupprimer = null },
            title = { Text(text = stringResource(R.string.signets_dialog_titre)) },
            text = {
                Text(
                    text = stringResource(
                        R.string.signets_dialog_message,
                        signet.description.ifBlank { signet.titre }
                    )
                )
            },
            confirmButton = {
                TextButton(onClick = {
                    viewModel.supprimerSignet(signet)
                    signetASupprimer = null
                }) {
                    Text(text = stringResource(R.string.signets_dialog_confirmer))
                }
            },
            dismissButton = {
                TextButton(onClick = { signetASupprimer = null }) {
                    Text(text = stringResource(R.string.signets_dialog_annuler))
                }
            }
        )
    }
}

@Composable
private fun ListeVide(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = stringResource(R.string.signets_liste_vide),
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Composable
private fun CarteSignet(
    signet: Signet,
    onModifier: () -> Unit,
    onSupprimer: () -> Unit
) {
    val contexte = LocalContext.current

    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
        elevation = CardDefaults.cardElevation(defaultElevation = 3.dp)
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            Text(
                text = signet.titre,
                style = MaterialTheme.typography.titleMedium,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )

            Spacer(modifier = Modifier.height(6.dp))

            UrlCliquable(url = signet.url, context = contexte)

            if (signet.description.isNotBlank()) {
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = signet.description,
                    style = MaterialTheme.typography.bodyMedium,
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(
                        R.string.date_ajout,
                        formatDateCourt(signet.dateAjout)
                    ),
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                    IconButton(onClick = onModifier) {
                        Icon(
                            imageVector = Icons.Outlined.Edit,
                            contentDescription = stringResource(R.string.signets_modifier_cd)
                        )
                    }
                    IconButton(onClick = onSupprimer) {
                        Icon(
                            imageVector = Icons.Outlined.Delete,
                            contentDescription = stringResource(R.string.signets_supprimer_cd),
                            tint = MaterialTheme.colorScheme.error
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun UrlCliquable(url: String, context: Context) {
    val texte = buildAnnotatedString {
        val annotationTag = "URL"
        pushStringAnnotation(tag = annotationTag, annotation = url)
        withStyle(
            SpanStyle(
                color = MaterialTheme.colorScheme.primary,
                textDecoration = TextDecoration.Underline
            )
        ) {
            append(url)
        }
        pop()
    }

    ClickableText(
        text = texte,
        style = MaterialTheme.typography.bodyMedium,
        maxLines = 2,
        overflow = TextOverflow.Ellipsis,
        modifier = Modifier.fillMaxWidth(),
        onClick = { offset ->
            texte.getStringAnnotations(tag = "URL", start = offset, end = offset)
                .firstOrNull()
                ?.let { ouvrirUrlDansNavigateur(context, it.item) }
        }
    )
}

private fun ouvrirUrlDansNavigateur(context: Context, url: String) {
    runCatching {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        context.startActivity(intent)
    }.onFailure { throwable ->
        if (throwable !is ActivityNotFoundException) {
            throw throwable
        }
    }
}

private fun formatDateCourt(ms: Long): String =
    DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT).format(Date(ms))
