package com.reyesnathan.reyesnathan_exercice9_gestionnairesignets.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.reyesnathan.reyesnathan_exercice9_gestionnairesignets.data.entities.Signet
import com.reyesnathan.reyesnathan_exercice9_gestionnairesignets.data.repository.SignetRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import androidx.lifecycle.ViewModelProvider


/**
 * ViewModel servant d’intermédiaire entre le dépôt et l’interface utilisateur.
 *
 * Il gère l’état courant de la liste des signets et les opérations CRUD.
 *
 * Source : Fiche 53.6 – Utiliser le dépôt de données via le ViewModel
 */
class SignetViewModel(private val repository: SignetRepository) : ViewModel() {

    // === État exposé à l’UI ===
    private val _listeSignets = MutableStateFlow<List<Signet>>(emptyList())
    val listeSignets: StateFlow<List<Signet>> = _listeSignets

    init {
        // Récupère en continu la liste depuis le repository (Flow)
        viewModelScope.launch {
            repository.tousLesSignets.collectLatest { signets ->
                _listeSignets.value = signets
            }
        }
    }

    /* Inspiré de : Fiche 66.3 – Enregistrement */
    fun ajouterSignet(titre: String, url: String, description: String) {
        viewModelScope.launch {
            repository.insererSignet(titre, url, description)
        }
    }

    /** Supprime un signet existant */
    fun supprimerSignet(signet: Signet) {
        viewModelScope.launch {
            repository.supprimerSignet(signet)
        }
    }

    /** Vide entièrement la liste des signets */
    fun viderSignets() {
        viewModelScope.launch {
            repository.supprimerTous()
        }
    }
}

/**
 * 🧠 Explications de ChatGPT :
 *
 * viewModelScope → permet d’exécuter les coroutines dans le cycle de vie du ViewModel.
 *
 * collectLatest → observe le flux de la BD et met à jour _listeSignets dès qu’un changement survient.
 *
 * MutableStateFlow / StateFlow → architecture unidirectionnelle (ta règle de base pour tous les ViewModel).
 */