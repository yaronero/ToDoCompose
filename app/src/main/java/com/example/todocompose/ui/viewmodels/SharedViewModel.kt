package com.example.todocompose.ui.viewmodels

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todocompose.data.models.Priority
import com.example.todocompose.data.models.ToDoTask
import com.example.todocompose.data.repositories.DataStoreRepository
import com.example.todocompose.data.repositories.ToDoRepository
import com.example.todocompose.util.Action
import com.example.todocompose.util.Constants
import com.example.todocompose.util.RequestState
import com.example.todocompose.util.SearchAppBarState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SharedViewModel @Inject constructor(
    private val toDoRepository: ToDoRepository,
    private val dataStoreRepository: DataStoreRepository
) : ViewModel() {

    val action: MutableState<Action> = mutableStateOf(Action.NO_ACTION)

    val id: MutableState<Int> = mutableStateOf(ToDoTask.UNDEFINED_ID)
    val title: MutableState<String> = mutableStateOf("")
    val description: MutableState<String> = mutableStateOf("")
    val priority: MutableState<Priority> = mutableStateOf(Priority.LOW)

    private val _selectedTask = MutableStateFlow<RequestState<ToDoTask?>>(RequestState.Idle)
    val selectedTask: StateFlow<RequestState<ToDoTask?>> = _selectedTask.asStateFlow()

    val searchAppBarState: MutableState<SearchAppBarState> =
        mutableStateOf(SearchAppBarState.CLOSED)
    val searchTextState: MutableState<String> = mutableStateOf("")

    private val _searchedTasks = MutableStateFlow<RequestState<List<ToDoTask>>>(RequestState.Idle)
    val searchedTasks: StateFlow<RequestState<List<ToDoTask>>> = _searchedTasks.asStateFlow()

    private val _allTasks = MutableStateFlow<RequestState<List<ToDoTask>>>(RequestState.Idle)
    val allTasks: StateFlow<RequestState<List<ToDoTask>>> = _allTasks.asStateFlow()

    val lowPriorityTasks: StateFlow<RequestState<List<ToDoTask>>> =
        toDoRepository.sortByLowPriority.map {
            RequestState.Success(it)
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(),
            initialValue = RequestState.Success(emptyList())
        )

    val highPriorityTasks: StateFlow<RequestState<List<ToDoTask>>> =
        toDoRepository.sortByHighPriority.map {
            RequestState.Success(it)
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(),
            initialValue = RequestState.Success(emptyList())
        )

    private val _sortState = MutableStateFlow<RequestState<Priority>>(RequestState.Idle)
    val sortState: StateFlow<RequestState<Priority>> = _sortState

    init {
        getAllTasks()
        readSortState()
    }

    fun readSortState() {
        _sortState.value = RequestState.Loading
        try {
            viewModelScope.launch {
                dataStoreRepository.readSortState
                    .map { Priority.valueOf(it) }
                    .collect {
                        _sortState.value = RequestState.Success(it)
                    }
            }
        } catch (e: Exception) {
            _sortState.value = RequestState.Error(e)
        }
    }

    fun persistSortState(priority: Priority) {
        viewModelScope.launch(Dispatchers.IO) {
            dataStoreRepository.persistSortState(priority)
        }
    }

    fun searchDatabase(query: String) {
        _searchedTasks.value = RequestState.Loading
        try {
            viewModelScope.launch {
                toDoRepository.searchDatabase(query).collect { searchedTasks ->
                    _searchedTasks.value = RequestState.Success(searchedTasks)
                }
            }
        } catch (e: Exception) {
            _searchedTasks.value = RequestState.Error(e)
        }
        searchAppBarState.value = SearchAppBarState.TRIGGERED
    }

    fun getAllTasks() {
        _allTasks.value = RequestState.Loading
        try {
            viewModelScope.launch {
                toDoRepository.getAllTasks.collect {
                    _allTasks.value = RequestState.Success(it)
                }
            }
        } catch (e: Exception) {
            _allTasks.value = RequestState.Error(e)
        }
    }

    fun getSelectedTask(taskId: Int) {
        if (taskId == ToDoTask.UNDEFINED_ID) {
            _selectedTask.value = RequestState.Success(null)
            updateTaskFields(null)
        } else {
            _selectedTask.value = RequestState.Loading
            try {
                viewModelScope.launch {
                    toDoRepository.getSelectedTask(taskId).collect { toDoTask ->
                        toDoTask?.let {
                            _selectedTask.value = RequestState.Success(it)
                        }
                    }
                }
            } catch (e: Exception) {
                _selectedTask.value = RequestState.Error(e)
            }
        }
    }

    fun updateTaskFields(selectedTask: ToDoTask?) {
        if (selectedTask != null) {
            id.value = selectedTask.id
            title.value = selectedTask.title
            description.value = selectedTask.description
            priority.value = selectedTask.priority
        } else {
            id.value = ToDoTask.UNDEFINED_ID
            title.value = ""
            description.value = ""
            priority.value = Priority.LOW
        }
    }

    fun handleDatabaseActions(action: Action) {
        when (action) {
            Action.ADD -> {
                addToDoTask()
                clearAndCloseAppBar()
            }

            Action.UPDATE -> updateToDoTask()
            Action.DELETE -> deleteToDoTask()
            Action.DELETE_ALL -> deleteAllToDoTasks()
            Action.UNDO -> addToDoTask()
            else -> {}
        }
        this.action.value = Action.NO_ACTION
    }

    private fun addToDoTask() {
        viewModelScope.launch(Dispatchers.IO) {
            val toDoTask = ToDoTask(
                id = 0,
                title = title.value,
                priority = priority.value,
                description = description.value
            )
            toDoRepository.addTask(toDoTask = toDoTask)
        }
    }

    private fun updateToDoTask() {
        viewModelScope.launch(Dispatchers.IO) {
            val toDoTask = ToDoTask(
                id = id.value,
                title = title.value,
                priority = priority.value,
                description = description.value
            )
            toDoRepository.updateTask(toDoTask = toDoTask)
        }
    }


    private fun deleteToDoTask() {
        viewModelScope.launch(Dispatchers.IO) {
            val toDoTask = ToDoTask(
                id = id.value,
                title = title.value,
                priority = priority.value,
                description = description.value
            )
            toDoRepository.deleteTask(toDoTask = toDoTask)
        }
    }

    private fun deleteAllToDoTasks() {
        viewModelScope.launch(Dispatchers.IO) {
            toDoRepository.deleteAllTasks()
        }
    }

    fun updateTitle(text: String) {
        if (text.length < Constants.MAX_TITLE_LENGTH) {
            title.value = text
        }
    }

    fun validateFields(): Boolean = title.value.isNotBlank() && description.value.isNotBlank()

    fun clearAndCloseAppBar() {
        searchAppBarState.value = SearchAppBarState.CLOSED
        searchTextState.value = ""
    }
}