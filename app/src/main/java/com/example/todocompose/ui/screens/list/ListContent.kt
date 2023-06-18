package com.example.todocompose.ui.screens.list

import android.content.res.Configuration
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.DismissDirection
import androidx.compose.material.DismissValue
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FractionalThreshold
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.SwipeToDismiss
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.rememberDismissState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.todocompose.R
import com.example.todocompose.data.models.Priority
import com.example.todocompose.data.models.ToDoTask
import com.example.todocompose.ui.components.PriorityIndicatorCircle
import com.example.todocompose.ui.theme.HighPriorityColor
import com.example.todocompose.ui.theme.ToDoComposeTheme
import com.example.todocompose.ui.theme.taskItemBackgroundColor
import com.example.todocompose.ui.theme.taskItemTextColor
import com.example.todocompose.util.Action
import com.example.todocompose.util.LARGEST_PADDING
import com.example.todocompose.util.LARGE_PADDING
import com.example.todocompose.util.RequestState
import com.example.todocompose.util.SearchAppBarState
import com.example.todocompose.util.TASK_ITEM_ELEVATION
import kotlinx.coroutines.delay

@Composable
fun ListContent(
    allTasks: RequestState<List<ToDoTask>>,
    searchedTasks: RequestState<List<ToDoTask>>,
    lowPriorityTasks: RequestState<List<ToDoTask>>,
    highPriorityTasks: RequestState<List<ToDoTask>>,
    sortState: RequestState<Priority>,
    searchAppBarState: SearchAppBarState,
    onSwipeToDelete: (Action, ToDoTask) -> Unit,
    navigateToTaskScreen: (taskId: Int) -> Unit
) {
    if (sortState is RequestState.Success) {
        when {
            searchAppBarState == SearchAppBarState.TRIGGERED -> {
                if (searchedTasks is RequestState.Success) {
                    HandleListContent(
                        tasks = searchedTasks.data,
                        onSwipeToDelete,
                        navigateToTaskScreen = navigateToTaskScreen
                    )
                }
            }

            sortState.data == Priority.NONE -> {
                if (allTasks is RequestState.Success) {
                    HandleListContent(
                        tasks = allTasks.data,
                        onSwipeToDelete,
                        navigateToTaskScreen = navigateToTaskScreen
                    )
                }
            }

            sortState.data == Priority.LOW -> {
                if (lowPriorityTasks is RequestState.Success) {
                    HandleListContent(
                        tasks = lowPriorityTasks.data,
                        onSwipeToDelete,
                        navigateToTaskScreen = navigateToTaskScreen
                    )
                }
            }

            sortState.data == Priority.HIGH -> {
                if (highPriorityTasks is RequestState.Success) {
                    HandleListContent(
                        tasks = highPriorityTasks.data,
                        onSwipeToDelete,
                        navigateToTaskScreen = navigateToTaskScreen
                    )
                }
            }
        }
    }
}

@Composable
fun HandleListContent(
    tasks: List<ToDoTask>,
    onSwipeToDelete: (Action, ToDoTask) -> Unit,
    navigateToTaskScreen: (taskId: Int) -> Unit
) {
    if (tasks.isEmpty()) {
        EmptyContent()
    } else {
        DisplayTasks(
            tasks = tasks,
            onSwipeToDelete,
            navigateToTaskScreen = navigateToTaskScreen
        )
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun DisplayTasks(
    tasks: List<ToDoTask>,
    onSwipeToDelete: (Action, ToDoTask) -> Unit,
    navigateToTaskScreen: (taskId: Int) -> Unit
) {
    LazyColumn {
        items(
            items = tasks,
            key = { task ->
                task.id
            }
        ) { task ->
            val dismissState = rememberDismissState()
            val dismissDirection = dismissState.dismissDirection
            val isDismissed = dismissState.isDismissed(DismissDirection.EndToStart)

            if(isDismissed && dismissDirection == DismissDirection.EndToStart) {
                LaunchedEffect(key1 = true) {
                    delay(300)
                    onSwipeToDelete(Action.DELETE, task)
                }
            }

            val degrees by animateFloatAsState(
                targetValue = if (dismissState.targetValue == DismissValue.Default) {
                    0f
                } else {
                    -45f
                }
            )

            var itemAppeared by remember { mutableStateOf(false) }
            LaunchedEffect(key1 = Unit) {
                itemAppeared = true
            }

            AnimatedVisibility(
                visible = itemAppeared && !isDismissed,
                enter = expandVertically(
                    animationSpec = tween(
                        durationMillis = 300
                    )
                ),
                exit = shrinkVertically(
                    animationSpec = tween(
                        durationMillis = 300
                    )
                )
            ) {
                SwipeToDismiss(
                    state = dismissState,
                    directions = setOf(DismissDirection.EndToStart),
                    dismissThresholds = { FractionalThreshold(fraction = 0.2f) },
                    background = { RedBackground(degrees = degrees) }
                ) {
                    TaskItem(
                        toDoTask = task,
                        navigateToTaskScreen = navigateToTaskScreen
                    )
                }
            }
        }
    }
}


@Composable
fun RedBackground(degrees: Float) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(HighPriorityColor)
            .padding(horizontal = LARGEST_PADDING),
        contentAlignment = Alignment.CenterEnd
    ) {
        Icon(
            imageVector = Icons.Filled.Delete,
            contentDescription = stringResource(id = R.string.deleted_task),
            tint = Color.White,
            modifier = Modifier.rotate(degrees = degrees)
        )
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun TaskItem(
    toDoTask: ToDoTask,
    navigateToTaskScreen: (taskId: Int) -> Unit
) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        color = MaterialTheme.colors.taskItemBackgroundColor,
        shape = RectangleShape,
        elevation = TASK_ITEM_ELEVATION,
        onClick = {
            navigateToTaskScreen(toDoTask.id)
        }
    ) {
        Column(
            modifier = Modifier
                .padding(LARGE_PADDING)
                .fillMaxWidth()
        ) {
            Row {
                Text(
                    text = toDoTask.title,
                    color = MaterialTheme.colors.taskItemTextColor,
                    style = MaterialTheme.typography.h5,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1,
                    modifier = Modifier.weight(8f)
                )
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    contentAlignment = Alignment.TopEnd
                ) {
                    PriorityIndicatorCircle(priority = toDoTask.priority)
                }
            }
            Text(
                text = toDoTask.description,
                color = MaterialTheme.colors.taskItemTextColor,
                style = MaterialTheme.typography.subtitle1,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Composable
@Preview
@Preview(
    name = "Dark mode",
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
fun TaskItemPreview() {
    ToDoComposeTheme {
        TaskItem(
            toDoTask = ToDoTask(
                1,
                "Title",
                "Lorem ipsum dolor sit amet, consectetur adipiscing edlit, sed do eiusmod tempor labore fractionale de gusto merinjo",
                Priority.LOW
            ),
            navigateToTaskScreen = {}
        )
    }
}

@Composable
@Preview
fun RedBackgroundPreview() {
    Column(modifier = Modifier.height(100.dp)) {
        RedBackground(degrees = 0f)
    }
}