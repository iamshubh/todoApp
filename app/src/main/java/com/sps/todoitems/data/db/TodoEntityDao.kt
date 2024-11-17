package com.sps.todoitems.data.db;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import kotlinx.coroutines.flow.Flow

@Dao
interface TodoEntityDao {

  @Query("SELECT * FROM todo_items")
  suspend fun getAll(): Flow<List<TodoEntity>>

  @Query("SELECT * FROM todo_items where id = :id")
  suspend fun getById(id: Int)

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun insertItem(todoItem: TodoEntity): Long

  @Delete
  suspend fun deleteItem(todoItem: TodoEntity): Long
}