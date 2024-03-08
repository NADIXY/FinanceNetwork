package com.example.abschlussprojektmyapp.data.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.abschlussprojektmyapp.data.model.SavedNews



// Data Access Object für die Datenbank.
@Dao
interface BusinessNewsDatabaseDao {

    /**

     * Gibt eine Liste von SavedNews in absteigender Reihenfolge nach ID sortiert zurück.
     * @return LiveData-Objekt, das eine Liste von SavedNews enthält
     */
    @Query("SELECT * FROM SavedNews ORDER BY id DESC")
    fun getSavedNews() : LiveData<List<SavedNews>>

    /**

     * Fügt eine neue SavedNews zur Datenbank hinzu oder ersetzt eine vorhandene SavedNews.
     * @param savedNews Die hinzuzufügende oder zu ersetzende News
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSavedNews(savedNews: SavedNews)

    /**

     * Löscht eine bestimmte SavedNews aus der Datenbank.
     * @param savedNews Die zu löschende SavedNews
     */
    @Delete
    suspend fun deleteSavedNews(savedNews: SavedNews)

}

