package com.example.prayerly;

import android.app.Application;
import androidx.room.Room
import com.example.prayerly.data.PrayerDatabase

class PrayerApplication : Application() {
    lateinit var prayerDB: PrayerDatabase

    override fun onCreate() {
        super.onCreate()

        prayerDB = Room.databaseBuilder(
            this,
            PrayerDatabase::class.java, "prayer-database"
        ).build()
    }
}
