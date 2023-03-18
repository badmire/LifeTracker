package com.example.lifetracker.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

// Database handles TaskTemplate and Record types
@Database(entities = [TaskTemplate::class,TaskRecord::class],version = 1)
abstract class AppDB: RoomDatabase() {
    // Initialize Dao objects for calling later
    abstract fun recordDao(): TaskRecordDao
    abstract fun taskTemplateDao(): TaskTemplateDao

    // Initialize companion object for serving up DB instances
    companion object {
        // Instantiate shared container to hold database instance
        // Only one instance will ever exist due to the volatile keyword
        @Volatile private var instance: AppDB? = null

        // Construct new instance of the database for given context
        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(
                context,
                AppDB::class.java,
                "lifeTracker.db"
            ).build()

        // Check if there is an existing DB instance, build one if not, and return
        fun getInstance(context: Context): AppDB {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also {
                    instance = it
                }
            }
        }
    }
}