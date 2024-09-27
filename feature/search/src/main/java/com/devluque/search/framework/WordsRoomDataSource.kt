package com.devluque.search.framework

import com.devluque.core.database.DbWord
import com.devluque.core.database.WordsDao
import com.devluque.data.datasource.WordsLocalDataSource
import com.devluque.entities.Word
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import javax.inject.Inject

internal class WordsRoomDataSource @Inject constructor(private val wordsDao: WordsDao) : WordsLocalDataSource {
    override suspend fun insertWord(word: String) = wordsDao.insertWord(word.toDbWord())

    override fun searchWords(query: String): Flow<List<Word>> = wordsDao.searchWords(query.trimEnd().trimStart()).map { dbWords ->
        dbWords?.map { dbWord: DbWord ->
            dbWord.toDomain()
        }
    }.filterNotNull()

    override val getRecentWords: Flow<List<Word>> get() =
        wordsDao.getRecentWords().map { it?.map { it.toDomain() } }.filterNotNull()

    override suspend fun deleteWord(word: String) = wordsDao.deleteWord(word)

    private fun String.toDbWord(): DbWord {
        return DbWord(
            word = this
                .trimEnd()
                .trimStart()
                .lowercase()
        )
    }

    private fun DbWord.toDomain(): Word {
        return Word(
            word = this.word
                .replaceFirst(this.word.first(), this.word.first().uppercaseChar(),true)
        )
    }
}