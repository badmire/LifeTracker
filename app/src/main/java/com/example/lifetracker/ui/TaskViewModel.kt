package com.example.lifetracker.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.lifetracker.data.AppDB
import com.example.lifetracker.data.TaskRepository
import com.example.lifetracker.data.TaskTemplate
import com.example.lifetracker.data.TaskTemplateDao
import kotlinx.coroutines.launch

class TaskViewModel(application: Application): AndroidViewModel(application) {
    /*
     * Instantiate database related stuff.
     */
    private val dao: TaskTemplateDao = AppDB.getInstance(application).taskDao()
    private val repository = TaskRepository(dao=dao)

    private val _task = MutableLiveData<TaskTemplate?>(null)
    val task: LiveData<TaskTemplate?> = _task

    private val _error = MutableLiveData<Throwable?>(null)
    val error: LiveData<Throwable?> = _error

    private val _loading = MutableLiveData<Boolean>(false)
    val loading: LiveData<Boolean> = _loading

    fun loadTasks() {
        viewModelScope.launch {
            _loading.value = true
            val result = repository.loadTasks()
            _loading.value = false
            _error.value = result.exceptionOrNull()
            _task.value = result.getOrNull()
        }
    }
}
