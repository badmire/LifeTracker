package com.example.lifetracker.data.containers

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.Period

@Entity
data class TaskTemplate(
    @PrimaryKey val name: String,
    val type: Int, // Uses constants that are defined... somewhere
    //val period: Period, TODO: Implement variable time periods (Daily, monthly, etc.)
    val direction: Boolean, // True == up, False == down. For counting up/down to a target
    val goal: Int? = null, // Target value to determine doneness
    // TODO: Implement lists of options associated with task.
    //val options: List<String>, // Array of options to pick from (colors, days of the week, etc.)
) : java.io.Serializable
