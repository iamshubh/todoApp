package com.sps.todoitems.data

import com.sps.todoitems.data.db.TodoEntity
import com.sps.todoitems.data.db.TodoEntityDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

class TodoRepositoryImpl @Inject constructor(
    private val dao: TodoEntityDao,
) : TodoRepository {

    override suspend fun getItems(): Flow<List<TodoApiModel>> {
        return dao.getAll().map {
            it.map { entity ->
                TodoApiModel(
                    id = entity.id,
                    text = entity.text.orEmpty(),
                    timeStamp = entity.timeStamp,
                )
            }
        }
    }

    override suspend fun addItem(item: TodoApiModel) {
        withContext(Dispatchers.IO) {
            dao.insertItem(
                TodoEntity(
                    id = item.id,
                    text = item.text,
                    timeStamp = item.timeStamp,
                )
            )
        }
    }

    override suspend fun delete(id: Long) {
        withContext(Dispatchers.IO) {
            dao.deleteItem(TodoEntity(id = id))
        }
    }
}