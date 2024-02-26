package com.example.abschlussprojektmyapp.data.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.abschlussprojektmyapp.data.model.Note

/**

 * Data Access Object für die Datenbank.
 */
@Dao
interface BusinessNewsDatabaseDao {

    /**

     * Gibt eine Liste von Notizen in absteigender Reihenfolge nach ID sortiert zurück.
     * @return LiveData-Objekt, das eine Liste von Notizen enthält
     */
    @Query("SELECT * FROM Note ORDER BY id DESC")
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

