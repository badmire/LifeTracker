package com.example.lifetracker.ui

import android.app.Application
import androidx.lifecycle.*
import com.example.lifetracker.data.*
import kotlinx.coroutines.launch

class TaskViewModel(application: Application): AndroidViewModel(application) {
    /*
     * Instantiate database related stuff.
     */
    private val taskDao: TaskTemplateDao = AppDB.getInstance(application).taskTemplateDao()
    private val recordDao: TaskRecordDao = AppDB.getInstance(application).recordDao()
    private val repository = TaskRepository(taskDao=taskDao, recordDao=recordDao)

    private val _taskTemplates = MutableLiveData<List<TaskTemplate>>(null)
    val taskTemplates: LiveData<List<TaskTemplate>> = _taskTemplates

    private val _taskRecords = MutableLiveData<List<TaskRecord?>>(null)
    val taskRecords: LiveData<List<TaskRecord?>> = _taskRecords

    private val _error = MutableLiveData<Throwable?>(null)
    val error: LiveData<Throwable?> = _error

    private val _loading = MutableLiveData<Boolean>(false)
    val loading: LiveData<Boolean> = _loading

    fun getAllRecordsForTask(name: String) : LiveData<List<TaskRecord>> {
        return this.repository.getAllRecordsForTask(name).asLiveData()
    }

    fun debugHardcode() {
        viewModelScope.launch {
            repository.insertNewTask(TaskTemplate("Drink Water", 1, direction = true, 4))
            repository.insertNewTask(TaskTemplate("Ped Dogs", 4, direction = true, 4))
            repository.insertNewTask(TaskTemplate("Get Money", 1, direction = true, 1))
            repository.insertNewTask(TaskTemplate("Fuck Bitches", 1, direction = true, 69))
            repository.insertNewTask(TaskTemplate("Mood", 1, direction = true))
        }
    }

    fun loadTasks() {
        viewModelScope.launch {
            _loading.value = true
            // TODO: Fix this by making it an actual query to an API class
            //val result = repository.loadTasks()
            val result = null
            _loading.value = false
            //_error.value = result.exceptionOrNull()
            //_task.value = result.getOrNull()
        }
    }
}
