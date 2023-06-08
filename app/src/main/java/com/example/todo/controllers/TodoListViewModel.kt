package com.example.todo.controllers

import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.ViewModel
import com.example.todo.data.TodoItem
import com.example.todo.data.TodoListState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

class TodoListViewModel: ViewModel() {
    private val _todoListState = MutableStateFlow(TodoListState.EMPTY)
    val todoListState: StateFlow<TodoListState> = _todoListState

    fun addItem() {
        val newItem = TodoItem(_todoListState.value.newItemText)
        _todoListState.update { it.addItem(newItem) }
    }

    fun onNewItemTextChange(newTextFieldValue: String) {
        _todoListState.update { it.updateNewItemText(newTextFieldValue) }
    }

    fun onItemUpdated(item: TodoItem) {
        _todoListState.update { it.updateItem(item) }
    }
}