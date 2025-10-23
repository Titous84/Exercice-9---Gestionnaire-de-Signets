package com.reyesnathan.reyesnathan_exercice9_gestionnairesignets.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Entité représentant un signet stocké en base Room.
 *
 * @property id Identifiant unique (auto-généré).
 * @property titre Titre affiché à l'écran.
 * @property url Lien du site (ex. https://...).
 * @property description Texte optionnel.
 * @property dateAjout Horodatage en ms (System.currentTimeMillis()).
 *
 * Inspiré de : Fiche 53.2 – Modèle pour représenter les données (classe d'entité).
 */
@Entity(tableName = "signets")
data class Signet(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val titre: String,
    val url: String,
    val description: String = "",
    val dateAjout: Long = System.currentTimeMillis() // 🆕 pour la partie 2 (58.2)
)

/**
 * 🧠 Explications rapides de ChatGPT
 *
 * @Entity → indique à Room que cette classe correspond à une table de la base de données.
 *
 * tableName = "signets" → nom de la table dans SQLite.
 *
 * @PrimaryKey(autoGenerate = true) → l’ID s’incrémente automatiquement.
 *
 * Chaque propriété Kotlin = une colonne dans la table.
 */