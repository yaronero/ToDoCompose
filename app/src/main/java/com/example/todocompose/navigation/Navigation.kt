package com.example.todocompose.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.example.todocompose.ui.viewmodels.TaskListViewModel
import com.example.todocompose.ui.viewmodels.TaskViewModel

@Composable
fun SetupNavigation(
    navController: NavHostController,
    taskListViewModel: TaskListViewModel,
    taskViewModel: TaskViewModel
) {
    val screen = remember(navController) {
        Screens(navController = navController)
    }

    NavHost(
        navController = navController,
        startDestination = Screens.LIST_SCREEN
    ) {
        listComposable(
            navigateToTaskScreen = screen.task,
            taskListViewModel = taskListViewModel
        )
        taskComposable(
            navigateToListScreen = screen.list,
            taskViewModel = taskViewModel
        )
    }
}