package com.reyesnathan.reyesnathan_exercice9_gestionnairesignets.data.dao

import androidx.room.*
import com.reyesnathan.reyesnathan_exercice9_gestionnairesignets.data.entities.Signet
import kotlinx.coroutines.flow.Flow

/**
 * Interface DAO (Data Access Object) pour interagir avec la table des signets.
 *
 * Le DAO sert de couche intermédiaire entre la base de données Room et
 * le reste de l’application.
 *
 * Source : Fiche 53.3 – Le DAO : couche intermédiaire entre l’application et la BD
 */
@Dao
interface SignetDao {
    /**
     * Récupère tous les signets, triés par ordre alphabétique du titre.
     * Le Flow permet d’écouter les changements en temps réel.
     */
    @Query("SELECT * FROM signets ORDER BY titre ASC")
    fun obtenirTousLesSignets(): Flow<List<Signet>>

    /**
     * Récupère un signet précis à partir de son identifiant.
     */
    @Query("SELECT * FROM signets WHERE id = :id LIMIT 1")
    fun obtenirSignetParId(id: Int): Flow<Signet?>

    /**
     * Insère un nouveau signet dans la base de données.
     * Si un signet existe déjà avec le même ID, il est remplacé.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insererSignet(signet: Signet)

    /**
     * Supprime un signet spécifique.
     */
    @Update
    suspend fun mettreAJourSignet(signet: Signet)

    /**
     * Supprime tous les signets.
     */
    @Query("DELETE FROM signets")
    suspend fun supprimerTousLesSignets()

    /**
     * Supprime un signet spécifique.
     */
    @Delete
    suspend fun supprimerSignet(signet: Signet)
}

/**
 * 🧠 Explications de chatGPT :
 *
 * @Dao : indique à Room que c’est une interface de manipulation des données.
 *
 * @Query : contient les requêtes SQL à exécuter.
 *
 * Flow<List<Signet>> : permet à l’UI d’être mise à jour automatiquement dès qu’un changement se produit (logique recommandée par la fiche 53.6 et ta règle “toujours utiliser Flow”).
 *
 * suspend : permet d’exécuter ces opérations dans une coroutine (non bloquant).
 */