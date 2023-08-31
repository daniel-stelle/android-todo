package com.example.todo;

import android.app.Application;
import androidx.room.Room
import com.example.todo.data.TodoDatabase

class TodoApplication : Application() {
    lateinit var todoDB: TodoDatabase

    override fun onCreate() {
        super.onCreate()

        todoDB = Room.databaseBuilder(
            this,
            TodoDatabase::class.java, "todo-database"
        ).build()
    }
}
