package com.sps.todoitems.data.db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [TodoEntity::class], version = 1, exportSchema = false)
abstract class TodoDatabase : RoomDatabase() {

    companion object {
        const val DATABASE_NAME = "todo_items"
    }

    abstract fun todoDao(): TodoEntityDao
}