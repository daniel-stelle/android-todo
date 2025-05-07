package com.example.prayerly.controllers

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.prayerly.PrayerApplication
import com.example.prayerly.data.PrayerDatabase
import com.example.prayerly.data.PrayerItem
import com.example.prayerly.data.PrayerListState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class PrayerListViewModel(application: Application) : AndroidViewModel(application) {
    private val _prayerListState = MutableStateFlow(PrayerListState.EMPTY)
    val prayerListState: StateFlow<PrayerListState> = _prayerListState
    private val db: PrayerDatabase = (application as PrayerApplication).prayerDB

    init {
        viewModelScope.launch(Dispatchers.IO) {
            db.prayerDao().getPrayerItems().collect { latestItems ->
                _prayerListState.update { it.copy(prayerItems = latestItems) }
            }
        }
    }

    fun addItem() {
        val newItem = PrayerItem(_prayerListState.value.newItemText)
        viewModelScope.launch(Dispatchers.IO) {
            db.prayerDao().upsertPrayerItem(newItem)
        }
    }

    fun onItemUpdated(item: PrayerItem) {
        if (item.text.isBlank()) {
            onItemDeleted(item)
            return
        }

        viewModelScope.launch(Dispatchers.IO) {
            db.prayerDao().upsertPrayerItem(item)
        }
    }

    fun onItemDeleted(item: PrayerItem) {
        viewModelScope.launch(Dispatchers.IO) {
            db.prayerDao().deletePrayerItem(item)
        }
    }

    fun onNewItemTextChange(newTextFieldValue: String) {
        _prayerListState.update { it.updateNewItemText(newTextFieldValue) }
    }

}