package com.example.todo.controllers

import androidx.lifecycle.ViewModel
import com.example.todo.data.TodoItem
import com.example.todo.data.TodoListState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

class TodoListViewModel: ViewModel() {
    private val _todoListState = MutableStateFlow(TodoListState.EMPTY)
    val todoListState: StateFlow<TodoListState> = _todoListState

    fun addItem(newItem: TodoItem) {
        _todoListState.update { it.addItem(newItem) }
    }
}