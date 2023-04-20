package com.example.lifetracker.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.lifetracker.utils.milisecondToString
import java.time.Period

@Entity
data class TaskTemplate(
    @PrimaryKey val name: String,
    val type: Int, // Uses constants that are defined... somewhere
    val period: Int, // Tracked in number of days
    val direction: Boolean? = null, // True == up, False == down. For counting up/down to a target
    val goal: Int? = null, // Target value to determine doneness
    val options: List<String>? = null, // Array of options to pick from (colors, days of the week, etc.)
    val date_added: Long = System.currentTimeMillis(),
) : java.io.Serializable
