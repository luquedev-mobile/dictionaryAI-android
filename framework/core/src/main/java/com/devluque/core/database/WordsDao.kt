package com.devluque.core.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface WordsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWord(word: DbWord)

    @Query("SELECT * from words where word like '%' || :query || '%'")
    fun searchWords(query: String): Flow<List<DbWord>?>

    @Query("SELECT * FROM words ORDER BY createdAt DESC LIMIT 5")
    fun getRecentWords(): Flow<List<DbWord>?>

    @Query("DELETE FROM words WHERE word = :word")
    suspend fun deleteWord(word: String)
}