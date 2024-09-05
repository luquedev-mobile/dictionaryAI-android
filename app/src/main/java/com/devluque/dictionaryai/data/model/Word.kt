package com.devluque.dictionaryai.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "words")
data class Word (
    @PrimaryKey
    val word: String = "",
    val createdAt: Long = System.currentTimeMillis()
)

fun String.toWord(): Word {
    return Word(
        word = this
            .lowercase()
            .replaceFirst(this.first(), this.first().uppercaseChar())
    )
}