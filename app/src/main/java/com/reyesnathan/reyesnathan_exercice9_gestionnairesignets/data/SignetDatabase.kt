package com.reyesnathan.reyesnathan_exercice9_gestionnairesignets.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.reyesnathan.reyesnathan_exercice9_gestionnairesignets.data.dao.SignetDao
import com.reyesnathan.reyesnathan_exercice9_gestionnairesignets.data.entities.Signet

/**
 * Base de données Room contenant la table des signets.
 *
 * - Version 2 : ajout de la colonne "dateAjout" (58.2).
 * - Pré-remplissage de quelques données de démo à la création (58.1).
 *
 * Sources :
 *  - Fiche 53.5 – Classe qui hérite de RoomDatabase
 *  - Fiche 58.1 – Ajouter des données initiales
 *  - Fiche 58.2 – Gérer les versions de la base de données
 */
@Database(entities = [Signet::class], version = 2, exportSchema = false) // 🆙 v2
abstract class SignetDatabase : RoomDatabase() {

    abstract fun signetDao(): SignetDao

    companion object {
        @Volatile
        private var INSTANCE: SignetDatabase? = null

        /**
         * Migration 1→2 : ajoute la colonne non nulle "dateAjout" (en ms).
         * On initialise à "now" pour les lignes existantes.
         */
        private val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(db: SupportSQLiteDatabase) {
                // Ajout de la colonne avec une valeur par défaut 0
                db.execSQL("ALTER TABLE signets ADD COLUMN dateAjout INTEGER NOT NULL DEFAULT 0")
                // Met à maintenant (en ms) les anciennes lignes
                db.execSQL("UPDATE signets SET dateAjout = (strftime('%s','now') * 1000) WHERE dateAjout = 0")
            }
        }

        /**
         * Callback appelé uniquement à la première création de la BD.
         * On insère 2 signets de démonstration avec du SQL direct (58.1).
         */
        private val PREPOPULATE = object : Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                db.execSQL("""
                    INSERT INTO signets (id, titre, url, description, dateAjout) VALUES
                    (NULL, 'Kotlin Lang', 'https://kotlinlang.org', 'Langage Kotlin', (strftime('%s','now')*1000)),
                    (NULL, 'Android Developers', 'https://developer.android.com', 'Docs Android', (strftime('%s','now')*1000))
                """.trimIndent())
            }
        }

        fun obtenirInstance(context: Context): SignetDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    SignetDatabase::class.java,
                    "signet_database"
                )
                    .addMigrations(MIGRATION_1_2) // ✅ on conserve les données de l’app v1
                    .addCallback(PREPOPULATE)     // ✅ données initiales pour une 1ère installation
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}

/**
 * 🧠 Explications de ChatGPT
 *
 * @Database : indique à Room quelles entités composent la BD.
 *
 * abstract fun signetDao() : permet d’accéder à ton DAO.
 *
 * Room.databaseBuilder() : construit la BD dans l’espace interne de l’app.
 *
 * fallbackToDestructiveMigration() : en cas de changement de version de schéma, supprime et recrée la BD (utile en développement).
 *
 * Le pattern Singleton empêche la création de plusieurs instances de la BD (bonne pratique Room).
 */