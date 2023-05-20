package com.example.todocompose.data.database

import androidx.room.*
import kotlinx.coroutines.flow.Flow

private const val todo_table = ToDoTaskEntity.DATABASE_TABLE_NAME

@Dao
interface ToDoDao {

    @Query("SELECT * FROM $todo_table ORDER BY id ASC")
    fun getAllTasks(): Flow<List<ToDoTaskEntity>>

    @Query("SELECT * FROM $todo_table WHERE id =:taskId")
    fun getSelectedTask(taskId: Int): Flow<ToDoTaskEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addTask(toDoTask: ToDoTaskEntity)

    @Update
    suspend fun updateTask(toDoTask: ToDoTaskEntity)

    @Delete
    suspend fun deleteTask(toDoTask: ToDoTaskEntity)

    @Query("DELETE FROM $todo_table")
    suspend fun deleteAllTask()

    @Query("SELECT * FROM $todo_table WHERE title LIKE :searchQuery OR description LIKE :searchQuery")
    fun searchDatabase(searchQuery: String): Flow<List<ToDoTaskEntity>>

    @Query("SELECT * FROM $todo_table ORDER BY CASE WHEN priority LIKE 'L%' THEN 1 WHEN priority LIKE 'M%' THEN 2 WHEN priority LIKE 'H%' THEN 3 END")
    fun sortByLowPriority(): Flow<List<ToDoTaskEntity>>

    @Query("SELECT * FROM $todo_table ORDER BY CASE WHEN priority LIKE 'H%' THEN 1 WHEN priority LIKE 'M%' THEN 2 WHEN priority LIKE 'L%' THEN 3 END")
    fun sortByHighPriority(): Flow<List<ToDoTaskEntity>>
}