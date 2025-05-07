package com.example.prayerly.screens

import androidx.compose.foundation.ExperimentalFoundationApi
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.prayerly.components.PrayerCard
import com.example.prayerly.controllers.PrayerListViewModel
import com.example.prayerly.data.PrayerItem
import com.example.prayerly.data.PrayerListState

@Composable
fun PrayerListScreen(
    viewModel: PrayerListViewModel,
    modifier: Modifier = Modifier
) {
    val prayerListState = viewModel.prayerListState.collectAsState()
    PrayerListScreen(
        modifier = modifier,
        state = prayerListState.value,
        onItemUpdated = viewModel::onItemUpdated,
        onDeleteItem = viewModel::onItemDeleted
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PrayerListScreen(
    state: PrayerListState,
    onItemUpdated: (PrayerItem) -> Unit,
    onDeleteItem: (PrayerItem) -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        modifier,
        topBar = {
            TopAppBar(title = { Text(text = "My prayer List") })
        }
    ) {
        val focusRequester = remember { FocusRequester() }

        PrayerListContainer(
            modifier = Modifier.padding(it),
            prayerListState = state,
            onItemUpdated = onItemUpdated,
            onDeleteItem = onDeleteItem
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PrayerListContainer(
    prayerListState: PrayerListState,
    onItemUpdated: (PrayerItem) -> Unit,
    onDeleteItem: (PrayerItem) -> Unit,
    modifier: Modifier = Modifier
) {
    var showNewItemField by remember { mutableStateOf(false) }

    Box(modifier = modifier.fillMaxSize()) {
        LazyColumn(
            Modifier.fillMaxSize(),
            contentPadding = PaddingValues(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(prayerListState.prayerItems, { it.guid }) { item ->
                PrayerCard(
                    modifier = Modifier.animateItemPlacement(),
                    prayerItem = item,
                    onChanged = { prayerItem -> onItemUpdated(prayerItem) },
                    onDeleteItem = { onDeleteItem(item) }
                )
            }

            if (showNewItemField) {
                item {
                    val focusRequester = remember { FocusRequester() }

                    LaunchedEffect(key1 = null) {
                        focusRequester.requestFocus()
                    }

                    PrayerCard(
                        prayerItem = PrayerItem(),
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
private fun PrayerListScreenPreview() {
    val items = listOf(
        PrayerItem(guid = "1", text = "Mow the lawn", completed = false),
        PrayerItem(guid = "2", text = "Brush my hair", completed = false),
        PrayerItem(guid = "3", text = "Pet the dog", completed = true),
        PrayerItem(guid = "4", text = "Put away dishes", completed = false)
    )
    val state = PrayerListState(items, "")

    PrayerListScreen(state = state, {}, {})
}