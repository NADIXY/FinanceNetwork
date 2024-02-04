package com.example.abschlussprojektmyapp.data.local

/*

/**

 * Data Access Object für die AbschlussprojektMyApp-Datenbank.
 */
@Dao
interface AbschlussprojektMyAppDatabaseDao {
    /**

     * Gibt eine Liste von Notizen in absteigender Reihenfolge nach ID sortiert zurück.
     * @return LiveData-Objekt, das eine Liste von Notizen enthält
     */
    @Query("SELECT * FROM Note ORDER BY id DESC")
    //SELECT * FROM note_table ORDER BY id DESC
    fun getNotes() : LiveData<List<Note>>

    /**

     * Fügt eine neue Notiz zur Datenbank hinzu oder ersetzt eine vorhandene Notiz.
     * @param note Die hinzuzufügende oder zu ersetzende Notiz
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNote(note: Note)

    /**

     * Löscht eine bestimmte Notiz aus der Datenbank.
     * @param note Die zu löschende Notiz
     */
    @Delete
    suspend fun deleteNote(note: Note)
}
 */
