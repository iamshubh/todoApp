package com.sps.todoitems.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sps.todoitems.data.TodoApiModel
import com.sps.todoitems.domain.TodoRepository
import com.sps.todoitems.ui.UiAction.None
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SharedViewmodel @Inject constructor(
    private val repository: TodoRepository,
) : ViewModel() {

    private var _items: MutableStateFlow<List<TodoApiModel>> = MutableStateFlow(emptyList())
    val items = _items.asStateFlow()

    private var _actionData: MutableStateFlow<UiAction> = MutableStateFlow(None)
    val actionData = _actionData.asStateFlow()

    fun initialize() {
        viewModelScope.launch {
            repository.getItems().collect {
                println("items updated $it")
                _items.value = it
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

            is None -> {
                _actionData.value = None
            }

            else -> {}
        }
    }

    fun onAddItem(text: String) {
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

    fun onDeleteItem(itemId: Long) {
        viewModelScope.launch {
            repository.delete(itemId)
        }
    }

    private fun isValidItem(text: String): Boolean {
        return text.trim() != "Error"
    }
}