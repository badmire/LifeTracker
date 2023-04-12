package com.example.lifetracker.ui

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.example.lifetracker.api.GoogleDriveService
import com.example.lifetracker.data.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class TaskViewModel(application: Application): AndroidViewModel(application) {
    /*
     * Instantiate database related stuff.
     */
    private val taskDao: TaskTemplateDao = AppDB.getInstance(application).taskTemplateDao()
    private val recordDao: TaskRecordDao = AppDB.getInstance(application).recordDao()
    private val repository = TaskRepository(taskDao=taskDao, recordDao=recordDao)

    fun addTaskTemplate(task: TaskTemplate) {
        viewModelScope.launch {
            repository.insertNewTask(task)
        }
    }

    fun deleteTaskTemplate(task: TaskTemplate) {
        viewModelScope.launch {
            repository.deleteTask(task)
        }
    }

    fun addTaskRecord(record: TaskRecord) {
        viewModelScope.launch {
            repository.insertNewRecord(record)
        }
    }

    fun deleteTaskRecord(record: TaskRecord) {
        viewModelScope.launch {
            repository.deleteRecord(record)
        }
    }

    fun getAllRecordsForTask(name: String) : LiveData<List<TaskRecord>> {
        return this.repository.getAllRecordsForTask(name).asLiveData()
    }


    fun debugHardcode() {
        viewModelScope.launch {
            repository.insertNewTask(TaskTemplate(
                "Get Money",
                1,
                1,
                true,
                10000,
            ))
        }
    }

    val taskTemplates = repository.getAllTasks().asLiveData()
    val taskRecords = repository.getAllRecords().asLiveData()
    val latestRecordsFlow = repository.getLatestRecords().asLiveData()

    /*
     * The current error for the most recent API query is stored in this private property.  This
     * error is exposed to the outside world in immutable form via the public `error` property
     * below.
     */
    private val _error = MutableLiveData<Throwable?>(null)

    /**
     * This property provides the error associated with the most recent API query, if there is
     * one.  If there was no error associated with the most recent API query, it will be null.
     */
    val error: LiveData<Throwable?> = _error

    /*
     * The current loading state is stored in this private property.  This loading state is exposed
     * to the outside world in immutable form via the public `loading` property below.
     */
    private val _loading = MutableLiveData<Boolean>(false)

    /**
     * This property indicates the current loading state of an API query.  It is `true` if an
     * API query is currently being executed or `false` otherwise.
     */
    val loading: LiveData<Boolean> = _loading

    fun loadTasks() {
        viewModelScope.launch {
            _loading.value = true
//            val result = repository.loadTasks()
            _loading.value = false
//            _error.value = result.exceptionOrNull()
            // _forecast.value = result.getOrNull()
        }
    }
}
