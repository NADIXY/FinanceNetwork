package com.example.abschlussprojektmyapp.data.local
/*

/**

 * Definiert eine Datenbankklasse mit einer Entitätsklasse und einer Versionsnummer
 */
@Database(entities = [Note::class], version = 1)
abstract class AbschlussprojektMyAppDatabase : RoomDatabase() {
    abstract val dao: AbschlussprojektMyAppDatabaseDao
}

/**

 * Deklariert eine statische, private und späte Initialisierung einer Instanz der AbschlussprojektMyAppDatabase-Klasse
 */
private lateinit var INSTANCE: AbschlussprojektMyAppDatabase

/**

 * Diese Funktion gibt eine Instanz der Datenbank zurück.
 * @param context Der Kontext der Anwendung
 * @return Die Instanz der AbschlussprojektMyAppDatabase
 */
fun getDatabase(context: Context): AbschlussprojektMyAppDatabase {
    synchronized(AbschlussprojektMyAppDatabase::class.java) {
        if (!::INSTANCE.isInitialized) {

            INSTANCE = Room.databaseBuilder(
                context.applicationContext,
                AbschlussprojektMyAppDatabase::class.java,
                "data_database"
            ).build()
        }
        return INSTANCE

    }
}
}

 */