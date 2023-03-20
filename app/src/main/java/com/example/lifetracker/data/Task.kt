package com.example.lifetracker.data


// Packages up TaskTemplate and Records for easy transit around the application
data class Task(
    val template: TaskTemplate,
    val records: List<TaskRecord>
)