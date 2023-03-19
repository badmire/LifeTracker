package com.example.lifetracker.data

import androidx.room.Entity

@Entity(primaryKeys = ["stamp","template"])
data class TaskRecord(
    val stamp: Long,
    val note: String?,
    val template: String
)
