package com.sps.todoitems.ui

import app.cash.turbine.test
import com.sps.todoitems.domain.TodoRepository
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Test


class SharedViewmodelTest {

    private val mockRepository = mockk<TodoRepository>(relaxed = true)
    private val systemUnderTest = SharedViewmodel(mockRepository)

    @OptIn(ExperimentalCoroutinesApi::class)
    private val testDispatcher = UnconfinedTestDispatcher()

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
    }

    @Test
    fun `initialize works fine`() = runTest {
        systemUnderTest.initialize()

        coVerify { mockRepository.getItems() }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `item addition works fine`() = runTest {
        systemUnderTest.onHandleAction(UiAction.ItemAddition(text = "todo"))

        coVerify { mockRepository.addItem(any()) }
        advanceUntilIdle()

        systemUnderTest.actionData.test {
            val uiAction = awaitItem()

            assert(uiAction is UiAction.SuccessAddition)
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `item addition works fine 2`() = runTest {
        systemUnderTest.onHandleAction(UiAction.ItemAddition(text = "Error"))

        coVerify(exactly = 0) { mockRepository.addItem(any()) }

        advanceUntilIdle()
        systemUnderTest.actionData.test {
            val uiAction = awaitItem()

            assert(uiAction is UiAction.FailureAddition)
        }
    }

    @Test
    fun `item deletion works fine`() = runTest {
        systemUnderTest.onHandleAction(UiAction.ItemDeletion(123L))

        coVerify { mockRepository.delete(123L) }
    }

}