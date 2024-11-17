package com.sps.todoitems.data

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class TodoRepositoryImpl @Inject constructor() : TodoRepository {

    private val items: MutableList<TodoApiModel> = mutableListOf()

    override suspend fun getItems(): Flow<List<TodoApiModel>> {
        return flow {
            emit(items)
        }
    }

    override suspend fun addItem(item: TodoApiModel) {
        withContext(Dispatchers.IO) {
            items.add(item)
        }
    }

    override suspend fun delete(id: Int) {
        withContext(Dispatchers.IO) {
            items.removeIf { it.id == id }
        }
    }
}