package com.example.lifetracker.data

import android.util.Log
import androidx.arch.core.util.Function
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations.map
import androidx.lifecycle.Transformations.switchMap
import androidx.lifecycle.asLiveData
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

// The primary function of this class is to be the generic entrypoint into the DB
// Also handles all of the coroutines/async operations
class TaskRepository(
//    private val service: GoogleDriveService,
    private val taskDao: TaskTemplateDao,
    private val recordDao: TaskRecordDao,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) {
    // Insertion Functions
    suspend fun insertNewTask(template: TaskTemplate) = taskDao.insert(template)
    suspend fun insertNewRecord(record: TaskRecord) = recordDao.insert(record)

    // Deletion Functions
    suspend fun deleteTask(template: TaskTemplate) = taskDao.delete(template)
    suspend fun deleteRecord(record: TaskRecord) = recordDao.delete(record)

    // Fetcher Functions
    fun getAllTasks() = taskDao.getAllTasks()
    fun getAllRecords() = recordDao.getAllRecords()

    fun getAllRecordsForTask(name: String) = recordDao.getAllRecordsForTask(name)

    fun getTaskTemplateByName(name: String?) = taskDao.getTaskByName(name)
    fun getSpecificRecord(stamp: Int, template: String) = recordDao.getSpecificRecord(stamp,template)

    fun getLatestRecords() : Flow<Map<String, TaskRecord?>> {
        val thing = taskDao.getAllTasks().map {taskList ->
            taskList.associate {
                withContext(ioDispatcher) {
                    it.name to recordDao.getLatestRecord(it.name)
                }
            }
        }
        return thing
    }

}