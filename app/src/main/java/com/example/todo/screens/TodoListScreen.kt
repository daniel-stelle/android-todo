package com.example.todo.screens

import android.content.res.Resources.Theme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
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
        onNewItemTextChange = viewModel::onNewItemTextChange,
        addNewItem = viewModel::addItem,
        onItemUpdated = viewModel::onItemUpdated,
        onDeleteItem = viewModel::onItemDeleted
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TodoListScreen(
    state: TodoListState,
    onNewItemTextChange: (String) -> Unit,
    addNewItem: () -> Unit,
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
        TodoListContainer(
            modifier = Modifier.padding(it),
            todoListState = state,
            onNewItemTextChange = onNewItemTextChange,
            addNewItem = addNewItem,
            onItemUpdated = onItemUpdated,
            onDeleteItem = onDeleteItem
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TodoListContainer(
    todoListState: TodoListState,
    onNewItemTextChange: (String) -> Unit,
    addNewItem: () -> Unit,
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
                    onCheckChanged = { completed ->
                        onItemUpdated(item.copy(completed = completed))
                    },
                    onDeleteItem = {
                        onDeleteItem(item)
                    }
                )
            }

            if (showNewItemField) {
                item {
                    TextField(
                        modifier = modifier.fillMaxWidth(),
                        value = todoListState.newItemText,
                        onValueChange = onNewItemTextChange,
                        keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
                        keyboardActions = KeyboardActions(onDone = {
                            addNewItem()
                            showNewItemField = false
                        })
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

    TodoListScreen(state = state, onNewItemTextChange = {}, {}, {}, {})
}