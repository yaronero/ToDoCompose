package com.example.todocompose.data.repositories

import com.example.todocompose.data.database.ToDoDao
import com.example.todocompose.data.database.ToDoTaskEntity
import com.example.todocompose.data.mapper.ToDoTaskMapper
import com.example.todocompose.data.models.ToDoTask
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.transform
import javax.inject.Inject

class ToDoRepositoryImpl @Inject constructor(
    private val toDoDao: ToDoDao,
    private val toDoTaskMapper: ToDoTaskMapper
): ToDoRepository {

    override val getAllTasks: Flow<List<ToDoTask>> = toDoDao.getAllTasks().mapToFlowModelList()
    override val sortByLowPriority: Flow<List<ToDoTask>> = toDoDao.sortByLowPriority().mapToFlowModelList()
    override val sortByHighPriority: Flow<List<ToDoTask>> = toDoDao.sortByHighPriority().mapToFlowModelList()

    override fun getSelectedTask(taskId: Int): Flow<ToDoTask?> = toDoDao.getSelectedTask(taskId).mapToFlowModel()

    override suspend fun addTask(toDoTask: ToDoTask) {
        toDoDao.addTask(toDoTask.mapToEntity())
    }

    override suspend fun updateTask(toDoTask: ToDoTask) {
        toDoDao.updateTask(toDoTask.mapToEntity())
    }

    override suspend fun deleteTask(toDoTask: ToDoTask) {
        toDoDao.deleteTask(toDoTask.mapToEntity())
    }

    override suspend fun deleteAllTasks() {
        toDoDao.deleteAllTask()
    }

    override fun searchDatabase(searchQuery: String): Flow<List<ToDoTask>> =
        toDoDao.searchDatabase(searchQuery).mapToFlowModelList()

    private fun Flow<ToDoTaskEntity?>.mapToFlowModel(): Flow<ToDoTask?> =
        this.transform {
            emit(it?.let {
                toDoTaskMapper.mapEntityToModel(it)
            })
        }

    private fun Flow<List<ToDoTaskEntity>>.mapToFlowModelList(): Flow<List<ToDoTask>> =
        this.transform { emit(toDoTaskMapper.mapEntityListToModelList(it)) }

    private fun ToDoTask.mapToEntity(): ToDoTaskEntity =
        toDoTaskMapper.mapModelToEntity(this)
}