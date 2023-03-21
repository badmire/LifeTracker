package com.example.lifetracker.data.containers

import androidx.room.Entity

@Entity(primaryKeys = ["stamp","template"])
data class TaskRecord(
    val stamp: Long,
    val template: String,
    val value: Int,
    val note: String? = null,
)
