package com.sps.todoitems.domain

import com.sps.todoitems.data.TodoApiModel
import kotlinx.coroutines.flow.Flow

interface TodoRepository {
    fun getItems(): Flow<List<TodoApiModel>>
    suspend fun addItem(item: TodoApiModel): Long
    suspend fun delete(id: Long): Int
}