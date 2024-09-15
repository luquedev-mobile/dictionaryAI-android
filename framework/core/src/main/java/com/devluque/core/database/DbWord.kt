package com.devluque.core.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "words")
data class DbWord (
    @PrimaryKey
    val word: String = "",
    val createdAt: Long = System.currentTimeMillis()
)