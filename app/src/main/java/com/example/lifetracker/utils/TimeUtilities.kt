package com.example.lifetracker.utils

import java.text.SimpleDateFormat

public val stamp_format = SimpleDateFormat("M/dd h:mm")

public fun milisecondToString(target: Long) : String {
    return stamp_format.format(target).toString()
}