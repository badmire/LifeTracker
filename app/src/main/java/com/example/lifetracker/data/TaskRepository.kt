package com.example.lifetracker.data

import com.example.lifetracker.api.GoogleDriveService
import kotlinx.coroutines.CoroutineDispatcher

// The primary function of this class is to be the generic entrypoint into the DB
// Also handles all of the coroutines/async operations
class TaskRepository(
    //private val service: GoogleDriveService,
    //private val ioDispatcher: CoroutineDispatcher,
    private val taskDao: TaskTemplateDao,
    private val recordDao: TaskRecordDao
) {
    // Insertion Functions
    suspend fun insertNewTask(template: TaskTemplate) = taskDao.insert(template)
    suspend fun insertNewRecord(record: TaskRecord) = recordDao.insert(record)

    // Deletion Functions
    suspend fun deleteTask(template: TaskTemplate) = taskDao.delete(template)
    suspend fun deleteTask(record: TaskRecord) = recordDao.delete(record)

    // Fetcher Functions
    fun getAllTaskTemplates() = taskDao.getAllTasks()
    fun getAllRecords() = recordDao.getAllTasks()

    fun getTaskTemplateByName(name: String?) = taskDao.getTaskByName(name)
    fun getSpecificRecord(stamp: Int, template: String) = recordDao.getSpecificRecord(stamp,template)
}