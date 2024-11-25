package com.sps.todoitems.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sps.todoitems.data.TodoApiModel
import com.sps.todoitems.domain.TodoRepository
import com.sps.todoitems.ui.UiAction.None
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SharedViewmodel @Inject constructor(
    private val repository: TodoRepository,
) : ViewModel() {

    private var _insertedItems: MutableStateFlow<List<TodoApiModel>> = MutableStateFlow(emptyList())
    val insertedItems = _insertedItems.asStateFlow()

    private var _filteredItems: MutableStateFlow<List<TodoApiModel>> = MutableStateFlow(emptyList())
    val filteredItems = _filteredItems.asStateFlow()

    private var _actionData: MutableStateFlow<UiAction> = MutableStateFlow(None)
    val actionData = _actionData.asStateFlow()

    private var _searchTextFlow: MutableStateFlow<String> = MutableStateFlow("")

    private var savedTodoItems: List<TodoApiModel> = emptyList()

    @OptIn(FlowPreview::class)
    fun initialize() {
        viewModelScope.launch {
            repository.getItems().collect {
                println("items updated $it")
                savedTodoItems = it
                _insertedItems.value = savedTodoItems
                _filteredItems.value = savedTodoItems
            }
        }

        viewModelScope.launch {
            _searchTextFlow
                .debounce(2000)
                .distinctUntilChanged().collect { query ->
                    if (query.isEmpty()) {
                        _filteredItems.value = savedTodoItems
                    } else {
                        _filteredItems.value = savedTodoItems.filter { it.text.contains(query) }
                    }
                }
        }
    }

    fun onHandleAction(uiAction: UiAction) {
        when (uiAction) {
            is UiAction.ItemAddition -> {
                onAddItem(uiAction.text)
            }

            is UiAction.ItemDeletion -> {
                onDeleteItem(uiAction.id)
            }

            is UiAction.ItemSearch -> {
                _searchTextFlow.tryEmit(uiAction.text)
            }

            is None -> {
                _actionData.value = None
            }

            else -> {}
        }
    }

    private fun onAddItem(text: String) {
        viewModelScope.launch {
            if (isValidItem(text).not()) {
                _actionData.value = UiAction.FailureAddition
                return@launch
            }
            _actionData.value = UiAction.Loading
            val itemId = repository.addItem(
                TodoApiModel(
                    text = text,
                    timeStamp = System.currentTimeMillis(),
                )
            )
            delay(2500)
            println("item added $itemId")
            _actionData.value = UiAction.SuccessAddition
        }
    }

    private fun onDeleteItem(itemId: Long) {
        viewModelScope.launch {
            repository.delete(itemId)
        }
    }

    private fun isValidItem(text: String): Boolean {
        return text.trim() != "Error"
    }
}