package com.example.todo.data

data class TodoListState(
    val todoItems: List<TodoItem>,
    val newItemText: String,
) {
    fun addItem(item: TodoItem) = this.copy(
        todoItems = todoItems + item,
        newItemText = ""
    )
    fun updateItem(item: TodoItem) = this.copy(
        todoItems = todoItems.map { if (it.guid == item.guid) item else it }
    )

    fun updateNewItemText(newItemText: String) = this.copy(newItemText = newItemText)

    companion object {
        val EMPTY = TodoListState(emptyList(), "")
    }
}