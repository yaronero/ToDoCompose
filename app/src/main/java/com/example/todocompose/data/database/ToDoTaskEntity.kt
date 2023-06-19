package com.example.todocompose.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.todocompose.data.database.ToDoTaskEntity.Companion.DATABASE_TABLE_NAME
import com.example.todocompose.data.models.Priority

@Entity(tableName = DATABASE_TABLE_NAME)
data class ToDoTaskEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val title: String,
    val description: String,
    val priority: Priority
) {
    companion object {
        const val DATABASE_TABLE_NAME = "todo_table"
    }
}