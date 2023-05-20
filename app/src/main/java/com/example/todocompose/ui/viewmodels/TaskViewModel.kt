package com.example.todocompose.ui.viewmodels

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todocompose.data.models.Priority
import com.example.todocompose.data.models.ToDoTask
import com.example.todocompose.data.repositories.ToDoRepository
import com.example.todocompose.util.RequestState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TaskViewModel @Inject constructor(
    private val repository: ToDoRepository
) : ViewModel() {

    val title: MutableState<String> = mutableStateOf("")
    val description: MutableState<String> = mutableStateOf("")
    val priority: MutableState<Priority> = mutableStateOf(Priority.LOW)

    private val _selectedTask = MutableStateFlow<RequestState<ToDoTask?>>(RequestState.Idle)
    val selectedTask: StateFlow<RequestState<ToDoTask?>> = _selectedTask.asStateFlow()

    fun getSelectedTask(taskId: Int) {
        if (taskId == ToDoTask.UNDEFINED_ID) {
            _selectedTask.value = RequestState.Success(null)
            updateTaskFields(null)
        } else {
            _selectedTask.value = RequestState.Loading
            try {
                viewModelScope.launch {
                    repository.getSelectedTask(taskId).collect {toDoTask ->
                        toDoTask?.let {
                            _selectedTask.value = RequestState.Success(it)
                            updateTaskFields(it)
                        }
                    }
                }
            } catch (e: Exception) {
                _selectedTask.value = RequestState.Error(e)
            }
        }    }

    private fun updateTaskFields(selectedTask: ToDoTask?) {
        if (selectedTask != null) {
            title.value = selectedTask.title
            description.value = selectedTask.description
            priority.value = selectedTask.priority
        } else {
            title.value = ""
            description.value = ""
            priority.value = Priority.LOW
        }
    }
}