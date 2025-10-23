package com.reyesnathan.reyesnathan_exercice9_gestionnairesignets.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.reyesnathan.reyesnathan_exercice9_gestionnairesignets.data.repository.SignetRepository

/**
 * Usine (factory) pour instancier SignetViewModel avec son dépôt.
 *
 * @see SignetViewModel
 * Inspiré de : Fiche 53.6 – Utiliser le dépôt de données via le ViewModel
 */
class SignetViewModelFactory(
    private val repository: SignetRepository
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modele: Class<T>): T {
        if (modele.isAssignableFrom(SignetViewModel::class.java)) {
            return SignetViewModel(repository) as T
        }
        throw IllegalArgumentException("Type de ViewModel non pris en charge : ${modele.name}")
    }
}