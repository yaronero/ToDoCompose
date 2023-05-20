package com.example.todocompose.data.mapper

import com.example.todocompose.data.database.ToDoTaskEntity
import com.example.todocompose.data.models.ToDoTask
import javax.inject.Inject

class ToDoTaskMapper @Inject constructor() {

    fun mapEntityToModel(toDoTaskEntity: ToDoTaskEntity): ToDoTask {
        return ToDoTask(
            id = toDoTaskEntity.id,
            title = toDoTaskEntity.title,
            description = toDoTaskEntity.description,
            priority = toDoTaskEntity.priority
        )
    }

    fun mapEntityListToModelList(toDoTaskEntityList: List<ToDoTaskEntity>): List<ToDoTask> = toDoTaskEntityList.map {
        mapEntityToModel(it)
    }

    fun mapModelToEntity(toDoTask: ToDoTask): ToDoTaskEntity {
        return ToDoTaskEntity(
            id = toDoTask.id,
            title = toDoTask.title,
            description = toDoTask.description,
            priority = toDoTask.priority
        )
    }
}