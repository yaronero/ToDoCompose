package com.example.todocompose.navigation

import androidx.navigation.NavHostController
import com.example.todocompose.util.Action

class Screens(navController: NavHostController) {

    val splash: () -> Unit = {
        navController.navigate(route = "list/${Action.NO_ACTION}") {
            popUpTo(SPLASH_SCREEN) { inclusive = true }
        }
    }

    val list: (Int) -> Unit = { taskId ->
        navController.navigate("task/$taskId") {
            popUpTo(TASK_SCREEN) { inclusive = true }
        }
    }

    val task: (Action) -> Unit = { action ->
        navController.navigate("list/${action.name}") {
            popUpTo(LIST_SCREEN) { inclusive = true }
        }
    }

    companion object {
        const val SPLASH_SCREEN = "list"

        const val LIST_SCREEN = "list/{action}"
        const val LIST_ARGUMENT_KEY = "action"

        const val TASK_SCREEN = "task/{taskId}"
        const val TASK_ARGUMENT_KEY = "taskId"
    }
}