package com.sps.todoitems.data.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "todos")
data class TodoEntity(
    @ColumnInfo
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,

    @ColumnInfo
    val text: String? = null,

    @ColumnInfo
    val timeStamp: Long = 0L,
)