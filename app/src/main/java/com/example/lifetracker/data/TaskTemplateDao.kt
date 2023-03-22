package com.example.lifetracker.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskTemplateDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(record: TaskTemplate)

    @Delete
    suspend fun delete(record: TaskTemplate)

    @Query("SELECT * FROM TaskTemplate ORDER BY date_added DESC")
    fun getAllTasks(): Flow<List<TaskTemplate>>

    @Query("SELECT * FROM TaskTemplate WHERE name = :name LIMIT 1")
    fun getTaskByName(name:String?): Flow<TaskTemplate>
}