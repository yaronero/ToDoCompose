package com.example.todocompose.ui.screens.task

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.example.todocompose.R
import com.example.todocompose.data.models.ToDoTask
import com.example.todocompose.ui.viewmodels.SharedViewModel
import com.example.todocompose.util.Action
import com.example.todocompose.util.RequestState
import com.example.todocompose.util.displayToast

@Composable
fun TaskScreen(
    selectedTask: RequestState<ToDoTask?>,
    sharedViewModel: SharedViewModel,
    navigateToListScreen: (Action) -> Unit
) {
    val title by sharedViewModel.title
    val description by sharedViewModel.description
    val priority by sharedViewModel.priority

    val context = LocalContext.current

    if(selectedTask is RequestState.Success) {
        Scaffold(
            topBar = {
                TaskAppBar(
                    title = selectedTask.data?.title,
                    navigateToListScreen = { action ->
                        if (action == Action.NO_ACTION) {
                            navigateToListScreen(action)
                        } else {
                            if (sharedViewModel.validateFields()) {
                                navigateToListScreen(action)
                            } else {
                                displayToast(context, context.getString(R.string.fields_empty))
                            }
                        }
                    }
                )
            }
        ) { paddingValues ->
            Surface(modifier = Modifier.padding(paddingValues)) {
                TaskContent(
                    title = title,
                    onTitleChange = { sharedViewModel.updateTitle(it) },
                    description = description,
                    onDescriptionChange = { sharedViewModel.description.value = it },
                    priority = priority,
                    onPrioritySelected = { sharedViewModel.priority.value = it }
                )
            }
        }
    }
}