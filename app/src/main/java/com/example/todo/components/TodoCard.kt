package com.example.todo.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Card
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.todo.data.TodoItem

@Composable
fun TodoCard(
    modifier: Modifier = Modifier,
    todoItem: TodoItem,
    onCheckChanged: (Boolean) -> Unit
) {
    Card(
        modifier.fillMaxWidth()
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
           Checkbox(
               checked = todoItem.completed,
               onCheckedChange = onCheckChanged
           )
           Text(text = todoItem.text)
        }
    }
}