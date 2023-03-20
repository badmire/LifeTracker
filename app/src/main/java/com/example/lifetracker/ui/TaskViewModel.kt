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

    val taskTemplates = repository.getAllTasks().asLiveData()
    val taskRecords = repository.getAllRecords().asLiveData()
}
