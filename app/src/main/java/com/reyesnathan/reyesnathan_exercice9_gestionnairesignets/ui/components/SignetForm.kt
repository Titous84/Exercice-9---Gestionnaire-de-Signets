package com.reyesnathan.reyesnathan_exercice9_gestionnairesignets.ui.components

import android.util.Patterns
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.reyesnathan.reyesnathan_exercice9_gestionnairesignets.R

/**
 * Formulaire réutilisable pour l’ajout ou la modification d’un signet.
 */
@Composable
fun SignetForm(
    initialTitre: String,
    initialUrl: String,
    initialDescription: String,
    submitLabel: String,
    modifier: Modifier = Modifier,
    onSubmit: (titre: String, url: String, description: String) -> Unit
) {
    var titre by rememberSaveable { mutableStateOf(initialTitre) }
    var url by rememberSaveable { mutableStateOf(initialUrl) }
    var description by rememberSaveable { mutableStateOf(initialDescription) }

    var titreTouched by rememberSaveable { mutableStateOf(false) }
    var urlTouched by rememberSaveable { mutableStateOf(false) }

    LaunchedEffect(initialTitre, initialUrl, initialDescription) {
        titre = initialTitre
        url = initialUrl
        description = initialDescription
        titreTouched = false
        urlTouched = false
    }

    val titreValide = titre.isNotBlank()
    val urlNormalisee = url.trim()
    val urlValide = urlNormalisee.isNotBlank() && Patterns.WEB_URL.matcher(urlNormalisee).matches()

    val titreEnErreur = titreTouched && !titreValide
    val urlEnErreur = urlTouched && !urlValide

    Column(modifier = modifier.fillMaxWidth()) {
        OutlinedTextField(
            value = titre,
            onValueChange = {
                titre = it
                if (!titreTouched) titreTouched = true
            },
            label = { Text(stringResource(R.string.label_titre)) },
            singleLine = true,
            isError = titreEnErreur,
            supportingText = {
                if (titreEnErreur) {
                    Text(text = stringResource(R.string.erreur_titre_obligatoire))
                }
            },
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = url,
            onValueChange = {
                url = it
                if (!urlTouched) urlTouched = true
            },
            label = { Text(stringResource(R.string.label_url)) },
            singleLine = true,
            isError = urlEnErreur,
            supportingText = {
                if (urlEnErreur) {
                    Text(
                        text = if (urlNormalisee.isBlank()) {
                            stringResource(R.string.erreur_url_obligatoire)
                        } else {
                            stringResource(R.string.erreur_url_invalide)
                        }
                    )
                }
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Uri,
                imeAction = ImeAction.Next
            ),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = description,
            onValueChange = { description = it },
            label = { Text(stringResource(R.string.label_description)) },
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
            minLines = 2,
            maxLines = 3,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = {
                titreTouched = true
                urlTouched = true

                if (titreValide && urlValide) {
                    onSubmit(titre.trim(), urlNormalisee, description.trim())
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = submitLabel, style = MaterialTheme.typography.labelLarge)
        }
    }
}
