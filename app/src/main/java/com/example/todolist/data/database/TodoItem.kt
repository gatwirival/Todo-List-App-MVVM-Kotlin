package com.example.todolist.data.database

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "todo")
@Parcelize()
data class TodoItem(@PrimaryKey(autoGenerate = true) val id: Long?,
                    @ColumnInfo(name = "title") val title: String,
                    @ColumnInfo(name = "description") val description: String?,
                    @ColumnInfo(name = "tags") val tags: String?,
                    @ColumnInfo(name = "due") val dueTime: Long?,
                    @ColumnInfo(name = "completed") var completed: Boolean): Parcelable