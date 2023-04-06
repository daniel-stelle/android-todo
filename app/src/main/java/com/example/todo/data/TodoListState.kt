package com.example.todo.data

data class TodoListState(
    val todoItems: List<TodoItem>
) {
    fun addItem(item: TodoItem) = this.copy(todoItems = todoItems + item)

    companion object {
        val EMPTY = TodoListState(emptyList())
    }
}