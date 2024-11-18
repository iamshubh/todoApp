package com.sps.todoitems.data.db;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import kotlinx.coroutines.flow.Flow

@Dao
interface TodoEntityDao {

    @Query("SELECT * FROM todos")
    fun getAll(): Flow<List<TodoEntity>>

    @Query("SELECT * FROM todos where id = :id")
    suspend fun getById(id: Int): TodoEntity

    @Insert
    suspend fun insertItem(todoItem: TodoEntity): Long

    @Delete
    suspend fun deleteItem(todoItem: TodoEntity): Int
}