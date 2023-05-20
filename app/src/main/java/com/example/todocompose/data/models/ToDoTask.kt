package com.example.todocompose.data.models

data class ToDoTask(
    val id: Int,
    val title: String,
    val description: String,
    val priority: Priority
) {
    companion object {
        const val UNDEFINED_ID = -1
    }
}