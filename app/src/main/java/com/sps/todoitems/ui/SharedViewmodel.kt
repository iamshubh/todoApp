package com.sps.todoitems.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sps.todoitems.data.TodoApiModel
import com.sps.todoitems.domain.TodoRepository
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

    private var _loading: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val loading = _loading.asStateFlow()

    fun initialize() {
        viewModelScope.launch {
            repository.getItems().collect {
                println("items updated $it")
                _items.value = it
            }
        }
    }

    fun onAddItem(text: String) {
        viewModelScope.launch {
            _loading.value = true
            val itemId = repository.addItem(
                TodoApiModel(
                    text = text,
                    timeStamp = System.currentTimeMillis(),
                )
            )
            delay(2500)
            println("item added $itemId")
            _loading.value = false
        }
    }

    suspend fun onDeleteItem(itemId: Long) {
        viewModelScope.launch {
            repository.delete(itemId)
        }
    }
}