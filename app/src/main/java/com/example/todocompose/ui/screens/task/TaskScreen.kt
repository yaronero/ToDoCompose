package com.example.todocompose.ui.screens.task

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
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

    val title = sharedViewModel.title
    val description = sharedViewModel.description
    val priority = sharedViewModel.priority

    val context = LocalContext.current

    BackHandler {
        navigateToListScreen(Action.NO_ACTION)
    }
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
                    onDescriptionChange = { sharedViewModel.updateDescription(it) },
                    priority = priority,
                    onPrioritySelected = { sharedViewModel.updatePriority(it) }
                )
            }
        }
    }
}