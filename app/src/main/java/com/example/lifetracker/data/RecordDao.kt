package com.example.lifetracker.data


import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskRecordDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(taskRecord: TaskRecord)

    @Delete
    suspend fun delete(taskRecord: TaskRecord)

    @Query("SELECT * FROM TaskRecord")
    fun getAllTasks(): Flow<List<TaskRecord>>

    @Query("SELECT * FROM TaskRecord WHERE stamp = :stamp, template = :template LIMIT 1")
    fun getSpecificRecord(stamp: Int, template: String): Flow<TaskRecord>

    // TODO: Build "completion" function to tell if a given period has a "successful" record.
}