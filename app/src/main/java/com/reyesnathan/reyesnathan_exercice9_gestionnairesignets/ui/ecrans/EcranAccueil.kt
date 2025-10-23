package com.reyesnathan.reyesnathan_exercice9_gestionnairesignets.ui.ecrans

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmarks
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

/**
 * Écran d’accueil simple : icône + message de bienvenue.
 *
 * Source : Fiche 63.1 – La navigation.
 */
@Composable
fun EcranAccueil() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Icône Material intégrée (plus besoin du drawable)
        Icon(
            imageVector = Icons.Filled.Bookmarks,
            contentDescription = "Icône signets",
            modifier = Modifier.size(120.dp),
            tint = MaterialTheme.colorScheme.primary
        )

        Spacer(Modifier.height(16.dp))

        Text(
            text = "Bienvenue dans le Gestionnaire de signets !",
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onBackground
        )
    }
}
