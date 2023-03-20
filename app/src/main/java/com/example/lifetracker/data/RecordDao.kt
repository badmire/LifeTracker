package com.example.lifetracker.data


import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskRecordDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(taskRecord: TaskRecord)

    @Delete
    suspend fun delete(taskRecord: TaskRecord)

    @Query("SELECT * FROM TaskRecord ORDER BY stamp DESC")
    fun getAllRecords(): Flow<List<TaskRecord>>

    @Query("SELECT * FROM TaskRecord WHERE template = :name")
    fun getAllRecordsForTask(name: String): Flow<List<TaskRecord>>

    @Query("SELECT * FROM TaskRecord WHERE stamp = :stamp, template = :template LIMIT 1")
    fun getSpecificRecord(stamp: Int, template: String): Flow<TaskRecord>

    @Query("SELECT * FROM TaskRecord WHERE template = :template LIMIT 1 ORDER BY stamp DESC")
    fun getLatestRecord(template: String): Flow<TaskRecord>

    // TODO: Build "completion" function to tell if a given period has a "successful" record.
}