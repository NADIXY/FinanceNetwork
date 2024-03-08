package com.example.abschlussprojektmyapp.data.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.abschlussprojektmyapp.data.model.SavedTopCurrency

@Dao
interface TopCurrencyDatabaseDao {

    /**

     * Gibt eine Liste von SavedTopCurrency in absteigender Reihenfolge nach ID sortiert zurück.
     * @return LiveData-Objekt, das eine Liste von SavedTopCurrency enthält
     */
    @Query("SELECT * FROM SavedTopCurrency")
    fun getTopCurrency() : LiveData<List<SavedTopCurrency>>

    /**

     * Fügt eine neue SavedTopCurrency zur Datenbank hinzu oder ersetzt eine vorhandene SavedTopCurrency.
     * @param SavedTopCurrency Die hinzuzufügende oder zu ersetzende TopCurrency
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTopCurrency(savedTopCurrency: SavedTopCurrency)

    /**

     * Löscht eine bestimmte savedTopCurrency aus der Datenbank.
     * @param savedTopCurrency Die zu löschende savedTopCurrency
     */
    @Delete
    suspend fun deleteTopCurrency(savedTopCurrency: SavedTopCurrency)

}