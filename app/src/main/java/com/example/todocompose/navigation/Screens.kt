package com.example.todocompose.navigation

import androidx.navigation.NavHostController
import com.example.todocompose.util.Action

class Screens(navController: NavHostController) {

    val list: (Action) -> Unit = { action ->
        navController.navigate("list/${action.name}") {
            popUpTo(LIST_SCREEN) { inclusive = true }
        }
    }

    val task: (Int) -> Unit = { taskId ->
        navController.navigate("task/$taskId") {
            popUpTo(TASK_SCREEN) { inclusive = true }
        }
    }

    companion object {
        const val LIST_SCREEN = "list/{action}"
        const val TASK_SCREEN = "task/{taskId}"

        const val LIST_ARGUMENT_KEY = "action"
        const val TASK_ARGUMENT_KEY = "taskId"
    }
}