package com.example.prayerly.data

import androidx.room.Dao
import androidx.room.Database
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.RoomDatabase
import kotlinx.coroutines.flow.Flow

@Dao
interface PrayerDAO {
    @Query("SELECT * FROM PrayerItem ORDER BY completed, createdAt")
    fun getPrayerItems(): Flow<List<PrayerItem>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun upsertPrayerItem(vararg prayerItems: PrayerItem)

    @Delete
    fun deletePrayerItem(item: PrayerItem)
}

@Database(
    entities = [PrayerItem::class],
    version = 1,
    exportSchema = false
)
abstract class PrayerDatabase : RoomDatabase() {
    abstract fun prayerDao(): PrayerDAO
}