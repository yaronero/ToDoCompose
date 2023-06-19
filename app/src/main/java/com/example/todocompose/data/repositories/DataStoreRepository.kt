package com.example.todocompose.data.repositories

import com.example.todocompose.data.models.Priority
import kotlinx.coroutines.flow.Flow

interface DataStoreRepository {

    suspend fun persistSortState(priority: Priority)

    val readSortState: Flow<String>

    companion object {
        const val PREFERENCE_NAME = "todo_preferences"
        const val PREFERENCE_SORT_KEY = "sort_state"
    }
}