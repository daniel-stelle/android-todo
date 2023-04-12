package com.example.todo.data

import androidx.compose.ui.text.input.TextFieldValue

data class TodoListState(
    val todoItems: List<TodoItem>,
    val newItemText: TextFieldValue,
) {
    fun addItem(item: TodoItem) = this.copy(todoItems = todoItems + item)
    fun updateNewItemText(newItemText: TextFieldValue) = this.copy(newItemText = newItemText)

    companion object {
        val EMPTY = TodoListState(emptyList(), TextFieldValue())
    }
}