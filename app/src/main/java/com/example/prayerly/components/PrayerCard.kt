package com.example.prayerly.components

import androidx.compose.foundation.clickable
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
import com.example.prayerly.R
import com.example.prayerly.data.PrayerItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PrayerCard(
    modifier: Modifier = Modifier,
    prayerItem: PrayerItem,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    focusRequester: FocusRequester = remember { FocusRequester() },
    onChanged: (PrayerItem) -> Unit,
    onDeleteItem: () -> Unit
) {
    var prayerText by remember { mutableStateOf(prayerItem.text) }
    val focused by interactionSource.collectIsFocusedAsState()
    var initialized by remember { mutableStateOf(false) }

    // Sooner
    // TODO: Move checked items to the bottom/Add toggle for showing or hiding checked

    // Later
    // TODO: Add sort - Sort alphabetically
    // TODO: Swipe to delete
    // TODO: Add ability to click TodoListScreen to unfocus active text field

    LaunchedEffect(key1 = focused) {
        if (!focused && initialized) {
            if (prayerText.isBlank()) {
                onDeleteItem()
            } else {
                onChanged(prayerItem.copy(text = prayerText))
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
                checked = prayerItem.completed,
                onCheckedChange = { onChanged(prayerItem.copy(completed = it)) }
            )
            TextField(
                modifier = Modifier.weight(1f).focusRequester(focusRequester),
                value = prayerText,
                onValueChange = { prayerText = it },
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
                    onChanged(prayerItem.copy(text = prayerText))
                })
            )
            Icon(
                imageVector = ImageVector.vectorResource(id = R.drawable.trash),
                contentDescription = "Delete prayer",
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
fun PrayerCardPreview() {
    PrayerCard(
        prayerItem = PrayerItem("Hello, world!"),
        onChanged = {},
        onDeleteItem = {}
    )
}