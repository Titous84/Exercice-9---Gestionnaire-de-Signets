package com.reyesnathan.reyesnathan_exercice9_gestionnairesignets.ui.ecrans

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmarks
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.reyesnathan.reyesnathan_exercice9_gestionnairesignets.R
import com.reyesnathan.reyesnathan_exercice9_gestionnairesignets.ui.components.PageTitle

/**
 * Page d’accueil simple mettant de l’avant le thème de l’application.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EcranAccueil() {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    PageTitle(
                        title = stringResource(R.string.accueil_page_title),
                        subtitle = stringResource(R.string.accueil_page_subtitle)
                    )
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(horizontal = 24.dp, vertical = 32.dp)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                imageVector = Icons.Filled.Bookmarks,
                contentDescription = stringResource(R.string.accueil_icone_description),
                modifier = Modifier.size(120.dp),
                tint = MaterialTheme.colorScheme.primary
            )

            Spacer(modifier = Modifier.size(24.dp))

            Text(
                text = stringResource(R.string.accueil_message_bienvenue),
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onBackground
            )

            Spacer(modifier = Modifier.size(12.dp))

            Text(
                text = stringResource(R.string.accueil_message_invitation),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}
