package com.example.todocompose.navigation

import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.todocompose.navigation.Screens.Companion.LIST_ARGUMENT_KEY
import com.example.todocompose.navigation.Screens.Companion.LIST_SCREEN
import com.example.todocompose.navigation.Screens.Companion.TASK_ARGUMENT_KEY
import com.example.todocompose.navigation.Screens.Companion.TASK_SCREEN
import com.example.todocompose.ui.screens.list.ListScreen
import com.example.todocompose.ui.screens.task.TaskScreen
import com.example.todocompose.ui.viewmodels.TaskListViewModel
import com.example.todocompose.ui.viewmodels.TaskViewModel
import com.example.todocompose.util.Action

fun NavGraphBuilder.listComposable(
    navigateToTaskScreen: (taskId: Int) -> Unit,
    taskListViewModel: TaskListViewModel
) {
    composable(
        route = LIST_SCREEN,
        arguments = listOf(navArgument(LIST_ARGUMENT_KEY) {
            type = NavType.StringType
        })
    ) {
        ListScreen(
            navigateToTaskScreen = navigateToTaskScreen,
            taskListViewModel = taskListViewModel
        )
    }
}

fun NavGraphBuilder.taskComposable(
    navigateToListScreen: (Action) -> Unit,
    taskViewModel: TaskViewModel
) {
    composable(
        route = TASK_SCREEN,
        arguments = listOf(navArgument(TASK_ARGUMENT_KEY) {
            type = NavType.IntType
        })
    ) { navBackStackEntry ->
        val taskId = navBackStackEntry.arguments!!.getInt(TASK_ARGUMENT_KEY)
        val selectedTask by taskViewModel.selectedTask.collectAsState()

        LaunchedEffect(key1 = Unit) {
            taskViewModel.getSelectedTask(taskId)
        }

        TaskScreen(
            selectedTask = selectedTask,
            taskViewModel = taskViewModel,
            navigateToListScreen = navigateToListScreen
        )
    }
}