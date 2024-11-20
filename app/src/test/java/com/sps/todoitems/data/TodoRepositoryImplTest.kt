package com.sps.todoitems.data

import app.cash.turbine.test
import com.sps.todoitems.data.db.TodoEntity
import com.sps.todoitems.data.db.TodoEntityDao
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class TodoRepositoryImplTest {

    private val mockDao = mockk<TodoEntityDao>(relaxed = true)
    private val systemUnderTest = TodoRepositoryImpl(dao = mockDao)

    private val dummyTodo1 = TodoEntity(id = 1)
    private val dummyTodo2 = TodoEntity(id = 2)

    private val dummyTodoApiModel1 = TodoApiModel(id = 1, text = "dummy", timeStamp = 123456789L)
    private val dummyTodoEntity = TodoEntity(text = "dummy", timeStamp = 123456789L)

    @Test
    fun `getting all todos works fine`() = runTest {
        every { mockDao.getAll() } returns flowOf(listOf(dummyTodo1, dummyTodo2))
        val insertedItems = systemUnderTest.getItems()

        insertedItems.test {
            val item = awaitItem()
            assert(item.size == 2)
            awaitComplete()
        }
    }

    @Test
    fun `add items work fine`() = runTest {
        systemUnderTest.addItem(dummyTodoApiModel1)

        coVerify {
            mockDao.insertItem(dummyTodoEntity)
        }
    }

    @Test
    fun `delete item work fine`() = runTest {
        systemUnderTest.delete(123)

        coVerify {
            mockDao.deleteItem(TodoEntity(id = 123))
        }
    }

}