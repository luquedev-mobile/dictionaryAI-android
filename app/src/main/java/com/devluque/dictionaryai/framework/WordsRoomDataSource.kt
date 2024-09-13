package com.devluque.dictionaryai.framework

import com.devluque.dictionaryai.data.datasource.WordsLocalDataSource
import com.devluque.dictionaryai.domain.Word
import com.devluque.dictionaryai.framework.database.DbWord
import com.devluque.dictionaryai.framework.database.WordsDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map

class WordsRoomDataSource(private val wordsDao: WordsDao) : WordsLocalDataSource {
    override suspend fun insertWord(word: String) = wordsDao.insertWord(word.toDbWord())
    override fun searchWords(query: String): Flow<List<Word>> = wordsDao.searchWords(query).map { dbWords ->
        dbWords?.map { dbWord: DbWord ->
            dbWord.toDomain()
        }
    }.filterNotNull()

    override val getRecentWords: Flow<List<Word>> =
        wordsDao.getRecentWords().map { it?.map { it.toDomain() } }.filterNotNull()

    override suspend fun deleteWord(word: String) = wordsDao.deleteWord(word)

    private fun String.toDbWord(): DbWord {
        return DbWord(
            word = this
                .lowercase()
                .replaceFirst(this.first(), this.first().uppercaseChar())
        )
    }

    private fun DbWord.toDomain(): Word {
        return Word(
            word = this.word
        )
    }
}