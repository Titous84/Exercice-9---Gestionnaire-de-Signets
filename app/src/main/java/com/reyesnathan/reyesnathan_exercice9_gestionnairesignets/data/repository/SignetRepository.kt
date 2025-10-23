/*
 Code généré par OpenAI. (2025). ChatGPT (GPT-5 Thinking, 2025-10-21) [Modèle de langage massif]. https://chat.openai.com
*/
package com.reyesnathan.reyesnathan_exercice9_gestionnairesignets.data.repository

import com.reyesnathan.reyesnathan_exercice9_gestionnairesignets.data.dao.SignetDao
import com.reyesnathan.reyesnathan_exercice9_gestionnairesignets.data.entities.Signet
import kotlinx.coroutines.flow.Flow

/**
 * Dépôt de données : couche intermédiaire entre le DAO et le ViewModel.
 *
 * Son rôle est d’encapsuler la logique d’accès aux données
 * et de déléguer les appels au DAO.
 *
 * Source : Fiche 53.4 – Le dépôt de données (repository)
 *          Fiche 66.3 – Enregistrement.
 */
class SignetRepository(private val signetDao: SignetDao) {

    /** Récupère la liste complète des signets sous forme de flux observable */
    val tousLesSignets: Flow<List<Signet>> = signetDao.obtenirTousLesSignets()

    /** Ajoute ou remplace un signet existant */
    suspend fun ajouterSignet(signet: Signet) {
        signetDao.insererSignet(signet)
    }

    /** Supprime un signet précis */
    suspend fun supprimerSignet(signet: Signet) {
        signetDao.supprimerSignet(signet)
    }

    /** Supprime tous les signets */
    suspend fun supprimerTous() {
        signetDao.supprimerTousLesSignets()
    }

    /* Inspiré de : Fiche 66.3 – Enregistrement */
    suspend fun insererSignet(titre: String, url: String, description: String) {
        signetDao.insererSignet(
            Signet(titre = titre, url = url, description = description)
        )
    }
}

/**
 * 🧠 Explications de ChatGPT :
 *
 * Ce dépôt agit comme passerelle propre et testable entre la base et la logique d’interface.
 *
 * Si un jour tu changes la source de données (ex. : API web au lieu de Room),
 * le ViewModel ne sera pas à modifier : seule cette couche le sera.
 *
 * Le Flow (issu du DAO) permet toujours de notifier automatiquement l’interface utilisateur
 * dès qu’un signet est ajouté ou supprimé.
 */
