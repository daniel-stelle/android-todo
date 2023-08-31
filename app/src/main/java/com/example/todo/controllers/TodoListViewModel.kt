package com.example.todo.controllers

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.todo.TodoApplication
import com.example.todo.data.TodoDatabase
import com.example.todo.data.TodoItem
import com.example.todo.data.TodoListState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class TodoListViewModel(application: Application) : AndroidViewModel(application) {
    private val _todoListState = MutableStateFlow(TodoListState.EMPTY)
    private val db: TodoDatabase = (application as TodoApplication).todoDB
    val todoListState: StateFlow<TodoListState> = _todoListState

    init {
        viewModelScope.launch(Dispatchers.IO) {
            db.todoDao().getTodoItems().collect { latestItems ->
                _todoListState.update { it.copy(todoItems = latestItems) }
            }
        }
    }

    fun addItem() {
        val newItem = TodoItem(_todoListState.value.newItemText)
        viewModelScope.launch(Dispatchers.IO) {
            db.todoDao().upsertTodoItem(newItem)
        }
    }

    fun onNewItemTextChange(newTextFieldValue: String) {
        _todoListState.update { it.updateNewItemText(newTextFieldValue) }
    }

    fun onItemUpdated(item: TodoItem) {
        _todoListState.update { it.updateItem(item) }
    }
}