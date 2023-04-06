package com.example.todo.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.todo.components.TodoCard
import com.example.todo.controllers.TodoListViewModel
import com.example.todo.data.TodoItem
import com.example.todo.data.TodoListState

@Composable
fun TodoListScreen(
    viewModel: TodoListViewModel,
    modifier: Modifier = Modifier
) {
    val todoListState = viewModel.todoListState.collectAsState()
    TodoListScreen(modifier, state = todoListState.value)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TodoListScreen(
    modifier: Modifier = Modifier,
    state: TodoListState
) {
    Scaffold(
        modifier,
        topBar = {
            TopAppBar(title = { Text(text = "My Todo List") })
        }
    ) {
        TodoListContainer(
            modifier = Modifier.padding(it),
            todoItems = state.todoItems
        )
    }
}

@Composable
fun TodoListContainer(
    modifier: Modifier = Modifier,
    todoItems: List<TodoItem>
) {
    Box(modifier = modifier.fillMaxSize()) {
        LazyColumn(
            Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(todoItems, { it.guid }) { item ->
                TodoCard(todoItem = item) {

                }
            }
        }

        Button(
            modifier = Modifier.align(Alignment.BottomEnd).padding(20.dp),
            shape = RoundedCornerShape(100.dp),
            onClick = { /*TODO*/ }
        ) {
            Text(text = "+")
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
    val state = TodoListState(items)

    TodoListScreen(state = state)
}