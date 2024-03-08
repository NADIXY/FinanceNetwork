package com.example.abschlussprojektmyapp.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.abschlussprojektmyapp.data.model.SavedTopCurrency

// Definiert eine Datenbankklasse mit einer Entitätsklasse und einer Versionsnummer
@Database(entities = [SavedTopCurrency::class], version = 1)
abstract class TopCurrencyDatabase : RoomDatabase() {
    abstract val dao: TopCurrencyDatabaseDao
}

// Deklariert eine statische, private und späte Initialisierung einer Instanz der Database-Klasse
private lateinit var INSTANCE: TopCurrencyDatabase

/**

 * Diese Funktion gibt eine Instanz der Datenbank zurück.
 * @param context Der Kontext der Anwendung
 * @return Die Instanz der database
 */
fun getTopCurrencyDatabase(context: Context): TopCurrencyDatabase {
    synchronized(TopCurrencyDatabase::class.java) {
        if (!::INSTANCE.isInitialized) {

            INSTANCE = Room.databaseBuilder(
                context.applicationContext,
                TopCurrencyDatabase::class.java,
                "db_saved_top_currency"
            ).build()
        }
        return INSTANCE

    }
}