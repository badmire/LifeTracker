package com.example.lifetracker.data

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

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

    fun getLatestRecord(template_name: String) = recordDao.getLatestRecord(template_name)

    // API stuff
//    suspend fun loadTasks() : Result<List<TaskTemplate?>> {
//        withContext(ioDispatcher) {
//            try {
//                val response = service.loadTasks(location, units, apiKey)
//                if (response.isSuccessful) {
//                    cachedForecast = response.body()  // Cache response forecast
//
//                    /*
//                     * Handle database related stuff.
//                     */
//                    val cityBookmark = CityBookmark(location!!, currentTime)
//                    dao.insert(cityBookmark)
//
//                    Result.success(cachedForecast) // Store successful forecast result in Result obj
//                } else {
//                    Result.failure(Exception(response.errorBody()?.string()))
//                }
//            } catch (e: Exception) {
//                Result.failure(e)
//            }
//        }
//    }
}