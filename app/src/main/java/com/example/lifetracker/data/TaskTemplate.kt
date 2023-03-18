package com.example.lifetracker.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.Period

@Entity
data class TaskTemplate(
    @PrimaryKey val name: String,
    val type: Int, // Uses constants that are defined... somewhere
    val period: Period,
    val direction: Boolean, // True == up, False == down. For counting up/down to a target
    val goal: Int?, // Target value to determine doneness
    val options: List<String>, // Array of options to pick from (colors, days of the week, etc.)
)
