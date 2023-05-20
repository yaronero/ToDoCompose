package com.example.todocompose.ui.screens.list

import androidx.compose.foundation.layout.padding
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import com.example.todocompose.R
import com.example.todocompose.data.models.ToDoTask
import com.example.todocompose.ui.theme.fabBackgroundColor
import com.example.todocompose.ui.viewmodels.TaskListViewModel
import com.example.todocompose.util.SearchAppBarState

@Composable
fun ListScreen(
    navigateToTaskScreen: (taskId: Int) -> Unit,
    taskListViewModel: TaskListViewModel
) {
    LaunchedEffect(key1 = true) {
        taskListViewModel.getAllTasks()
    }

    val allTasks by taskListViewModel.allTasks.collectAsState()
    val searchAppBarState: SearchAppBarState by taskListViewModel.searchAppBarState
    val searchTextState: String by taskListViewModel.searchTextState

    Scaffold(
        topBar = {
            ListAppBar(
                taskListViewModel = taskListViewModel,
                searchAppBarState = searchAppBarState,
                searchTextState = searchTextState
            )
        },
        floatingActionButton = {
            ListFAB(navigateToTaskScreen)
        }
    ) { paddingValues ->
        Surface(
            modifier = Modifier.padding(paddingValues)
        ) {
            ListContent(allTasks, navigateToTaskScreen)
        }
    }
}

@Composable
fun ListFAB(
    onFabClicked: (taskId: Int) -> Unit
) {
    FloatingActionButton(
        onClick = {
            onFabClicked(ToDoTask.UNDEFINED_ID)
        },
        backgroundColor = MaterialTheme.colors.fabBackgroundColor
    ) {
        Icon(
            imageVector = Icons.Filled.Add,
            contentDescription = stringResource(id = R.string.add_button),
            tint = Color.White
        )
    }
}