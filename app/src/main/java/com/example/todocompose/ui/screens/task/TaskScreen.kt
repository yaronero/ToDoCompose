package com.example.todocompose.ui.screens.task

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.example.todocompose.data.models.ToDoTask
import com.example.todocompose.ui.viewmodels.TaskViewModel
import com.example.todocompose.util.Action
import com.example.todocompose.util.RequestState

@Composable
fun TaskScreen(
    selectedTask: RequestState<ToDoTask?>,
    taskViewModel: TaskViewModel,
    navigateToListScreen: (Action) -> Unit
) {
    val title by taskViewModel.title
    val description by taskViewModel.description
    val priority by taskViewModel.priority

    if(selectedTask is RequestState.Success) {
        Scaffold(
            topBar = {
                TaskAppBar(
                    title = selectedTask.data?.title,
                    navigateToListScreen = navigateToListScreen
                )
            }
        ) { paddingValues ->
            Surface(modifier = Modifier.padding(paddingValues)) {
                TaskContent(
                    title = title,
                    onTitleChange = { taskViewModel.title.value = it },
                    description = description,
                    onDescriptionChange = { taskViewModel.description.value = it },
                    priority = priority,
                    onPrioritySelected = { taskViewModel.priority.value = it }
                )
            }
        }
    }
}