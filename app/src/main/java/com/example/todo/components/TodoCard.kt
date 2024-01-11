package com.example.todo.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.FocusInteraction
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Card
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.todo.R
import com.example.todo.data.TodoItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TodoCard(
    modifier: Modifier = Modifier,
    todoItem: TodoItem,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    focusRequester: FocusRequester = remember { FocusRequester() },
    onChanged: (TodoItem) -> Unit,
    onDeleteItem: () -> Unit
) {
    var todoText by remember { mutableStateOf(todoItem.text) }
    val focused by interactionSource.collectIsFocusedAsState()
    var initialized by remember { mutableStateOf(false) }

    // TODO: Add sort - Sort alphabetically
    // TODO: Swipe to delete
    // TODO: Move checked items to the bottom/Add toggle for showing or hiding checked
    // TODO: Add ability to click TodoListScreen to unfocus active text field

    LaunchedEffect(key1 = focused) {
        if (!focused && initialized) {
            if (todoText.isBlank()) {
                onDeleteItem()
            } else {
                onChanged(todoItem.copy(text = todoText))
            }
        }

        initialized = true
    }

    Card(
        modifier.fillMaxWidth()
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(end = 10.dp)
        ) {
            Checkbox(
                checked = todoItem.completed,
                onCheckedChange = { onChanged(todoItem.copy(completed = it)) }
            )
            TextField(
                modifier = Modifier.weight(1f).focusRequester(focusRequester),
                value = todoText,
                onValueChange = { todoText = it },
                singleLine = true,
                colors = TextFieldDefaults.textFieldColors(
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                ),
                interactionSource = interactionSource,
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Done,
                    capitalization = KeyboardCapitalization.Sentences
                ),
                keyboardActions = KeyboardActions(onDone = {
                    onChanged(todoItem.copy(text = todoText))
                })
            )
            Icon(
                imageVector = ImageVector.vectorResource(id = R.drawable.trash),
                contentDescription = "Delete todo",
                tint = MaterialTheme.colorScheme.error,
                modifier = Modifier
                    .clickable(onClick = onDeleteItem)
                    .padding(start = 10.dp, end = 5.dp)
            )
        }
    }
}

@Preview
@Composable
fun TodoCardPreview() {
    TodoCard(
        todoItem = TodoItem("Hello, world!"),
        onChanged = {},
        onDeleteItem = {}
    )
}