package com.example.abschlussprojektmyapp.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.abschlussprojektmyapp.data.model.Note

/**

* Definiert eine Datenbankklasse mit einer Entitätsklasse und einer Versionsnummer
*/
@Database(entities = [Note::class], version = 1)
abstract class BusinessNewsDatabase : RoomDatabase() {
    abstract val dao: BusinessNewsDatabaseDao
}

/**

 * Deklariert eine statische, private und späte Initialisierung einer Instanz der Database-Klasse
 */
private lateinit var INSTANCE: BusinessNewsDatabase

/**

 * Diese Funktion gibt eine Instanz der Datenbank zurück.
 * @param context Der Kontext der Anwendung
 * @return Die Instanz der database
 */
fun getDatabase(context: Context): BusinessNewsDatabase {
    synchronized(BusinessNewsDatabase::class.java) {
        if (!::INSTANCE.isInitialized) {

            INSTANCE = Room.databaseBuilder(
                context.applicationContext,
                BusinessNewsDatabase::class.java,
                "db_business_news"
            ).build()
        }
        return INSTANCE

    }
}

