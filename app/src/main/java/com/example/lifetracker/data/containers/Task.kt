package com.example.lifetracker.data.containers

import com.example.lifetracker.data.containers.TaskRecord
import com.example.lifetracker.data.containers.TaskTemplate


// Packages up TaskTemplate and Records for easy transit around the application
data class Task(
    val template: TaskTemplate,
    val records: List<TaskRecord>
)