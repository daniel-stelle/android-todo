package com.example.todo.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.todo.components.TodoCard
import com.example.todo.data.TodoItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TodoListScreen(
    modifier: Modifier = Modifier,
    todoItems: List<TodoItem>
) {
    Scaffold(
        modifier,
        topBar = {
            TopAppBar(title = { Text(text = "My Todo List") })
        }
    ) {
        TodoListContainer(
            modifier = Modifier.padding(it),
            todoItems = todoItems
        )
    }
}

@Composable
fun TodoListContainer(
    modifier: Modifier = Modifier,
    todoItems: List<TodoItem>
) {
    LazyColumn(
        modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(todoItems, { it.guid }) { item ->
            TodoCard(todoItem = item) {

            }
        }
    }
}

@Preview
@Composable
fun TodoListScreenPreview() {
    val items = listOf(
        TodoItem(guid = "1", text = "Mow the lawn", completed = false),
        TodoItem(guid = "2", text = "Brush my hair", completed = false),
        TodoItem(guid = "3", text = "Pet the dog", completed = true),
        TodoItem(guid = "4", text = "Put away dishes", completed = false)
    )

    TodoListScreen(todoItems = items)
}