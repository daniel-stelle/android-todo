package com.example.todo.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.example.todo.R
import com.example.todo.data.TodoItem

@Composable
fun TodoCard(
    modifier: Modifier = Modifier,
    todoItem: TodoItem,
    onCheckChanged: (Boolean) -> Unit,
    onDeleteItem: () -> Unit
) {
    Card(
        modifier.fillMaxWidth()
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(end = 10.dp)
        ) {
           Checkbox(
               checked = todoItem.completed,
               onCheckedChange = onCheckChanged
           )
           Text(
               text = todoItem.text,
               modifier = Modifier.weight(1f)
           )
           Icon(
               imageVector = ImageVector.vectorResource(id = R.drawable.trash),
               contentDescription = "Delete todo",
               tint = MaterialTheme.colorScheme.error,
               modifier = Modifier.clickable (onClick = onDeleteItem)
           )
        }
    }
}