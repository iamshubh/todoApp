package com.sps.todoitems.data.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "todo_items")
class TodoEntity(
    @ColumnInfo
    @PrimaryKey(autoGenerate = true)
    val id: Long = -1L,

    @ColumnInfo(name = "text")
    val text: String? = null,

    @ColumnInfo(name = "time")
    val timeStamp: Long = -1L,
)