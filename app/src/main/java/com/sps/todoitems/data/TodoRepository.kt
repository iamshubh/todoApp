package com.sps.todoitems.data

import kotlinx.coroutines.flow.Flow

interface TodoRepository {
    suspend fun getItems(): Flow<List<TodoApiModel>>
    suspend fun addItem(item: TodoApiModel)
    suspend fun delete(id: Int)
}