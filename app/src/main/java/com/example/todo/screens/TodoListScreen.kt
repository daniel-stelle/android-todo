package com.example.todo.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.focusable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
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
    TodoListScreen(
        modifier = modifier,
        state = todoListState.value,
        onItemUpdated = viewModel::onItemUpdated,
        onDeleteItem = viewModel::onItemDeleted
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TodoListScreen(
    state: TodoListState,
    onItemUpdated: (TodoItem) -> Unit,
    onDeleteItem: (TodoItem) -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        modifier,
        topBar = {
            TopAppBar(title = { Text(text = "My Todo List") })
        }
    ) {
        val focusRequester = remember { FocusRequester() }

        TodoListContainer(
            modifier = Modifier.padding(it),
            todoListState = state,
            onItemUpdated = onItemUpdated,
            onDeleteItem = onDeleteItem
        )
    }
}

@Composable
fun TodoListContainer(
    todoListState: TodoListState,
    onItemUpdated: (TodoItem) -> Unit,
    onDeleteItem: (TodoItem) -> Unit,
    modifier: Modifier = Modifier
) {
    var showNewItemField by remember { mutableStateOf(false) }

    Box(modifier = modifier.fillMaxSize()) {
        LazyColumn(
            Modifier.fillMaxSize(),
            contentPadding = PaddingValues(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(todoListState.todoItems, { it.guid }) { item ->
                TodoCard(
                    todoItem = item,
                    onChanged = { todoItem -> onItemUpdated(todoItem) },
                    onDeleteItem = { onDeleteItem(item) }
                )
            }

            if (showNewItemField) {
                item {
                    val focusRequester = remember { FocusRequester() }

                    LaunchedEffect(key1 = null) {
                        focusRequester.requestFocus()
                    }

                    TodoCard(
                        todoItem = TodoItem(),
                        onChanged = onItemUpdated,
                        focusRequester = focusRequester,
                        onDeleteItem = { showNewItemField = false }
                    )
                }
            }
        }

        Button(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(20.dp),
            shape = RoundedCornerShape(100.dp),
            onClick = { showNewItemField = true }
        ) {
            Text(text = "+")
        }
    }
}

@Preview
@Composable
private fun TodoListScreenPreview() {
    val items = listOf(
        TodoItem(guid = "1", text = "Mow the lawn", completed = false),
        TodoItem(guid = "2", text = "Brush my hair", completed = false),
        TodoItem(guid = "3", text = "Pet the dog", completed = true),
        TodoItem(guid = "4", text = "Put away dishes", completed = false)
    )
    val state = TodoListState(items, "")

    TodoListScreen(state = state, {}, {})
}