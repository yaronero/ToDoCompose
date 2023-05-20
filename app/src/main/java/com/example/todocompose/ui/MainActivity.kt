package com.example.todocompose.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.TopAppBar
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.todocompose.navigation.SetupNavigation
import com.example.todocompose.ui.theme.ToDoComposeTheme
import com.example.todocompose.ui.theme.topAppBarBackgroundColor
import com.example.todocompose.ui.viewmodels.TaskListViewModel
import com.example.todocompose.ui.viewmodels.TaskViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    
    private lateinit var navController: NavHostController
    private val taskListViewModel: TaskListViewModel by viewModels()
    private val taskViewModel: TaskViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ToDoComposeTheme {
                navController = rememberNavController()
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(MaterialTheme.colors.background)
                ) {
                    TopAppBar(
                        backgroundColor = MaterialTheme.colors.topAppBarBackgroundColor
                    ) { }
                    SetupNavigation(
                        navController = navController,
                        taskListViewModel = taskListViewModel,
                        taskViewModel = taskViewModel
                    )
                }
            }
        }
    }
}