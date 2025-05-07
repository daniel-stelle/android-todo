package com.example.prayerly.data

data class PrayerListState(
    val prayerItems: List<PrayerItem>,
    val newItemText: String
) {
    fun addItem(item: PrayerItem) = this.copy(
        prayerItems = prayerItems + item,
        newItemText = ""
    )

    fun updateItem(item: PrayerItem) = this.copy(
        prayerItems = prayerItems.map { if (it.guid == item.guid) item else it }
    )

    fun updateNewItemText(newItemText: String) = this.copy(newItemText = newItemText)

    companion object {
        val EMPTY = PrayerListState(emptyList(), "")
    }
}