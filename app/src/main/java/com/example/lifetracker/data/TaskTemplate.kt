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
    //val period: Period, TODO: Implement variable time periods (Daily, monthly, etc.)
    val direction: Boolean, // True == up, False == down. For counting up/down to a target
    val goal: Int? = null, // Target value to determine doneness
    val date_added: Long = System.currentTimeMillis()
    // TODO: Implement lists of options associated with task.
    //val options: List<String>, // Array of options to pick from (colors, days of the week, etc.)
) : java.io.Serializable
