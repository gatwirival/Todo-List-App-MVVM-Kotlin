package com.example.todolist.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [TodoItem::class], version = 1, exportSchema = false)
abstract class TodoItemDatabase : RoomDatabase() {

    abstract fun todoDao(): TodoItemDao

    companion object {
        private var INSTANCE: TodoItemDatabase? = null

        fun getInstance(context: Context): TodoItemDatabase? {
            if (INSTANCE == null) {
                synchronized(TodoItemDatabase::class) {
                    INSTANCE = Room.databaseBuilder(context,
                        TodoItemDatabase::class.java,
                        "todo_db")
                        .build()
                }
            }

            return INSTANCE
        }
    }

}