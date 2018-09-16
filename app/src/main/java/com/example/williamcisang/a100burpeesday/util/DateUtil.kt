package com.example.williamcisang.a100burpeesday.util

import java.util.*
import java.util.concurrent.TimeUnit

/**
 * Created by WCisang on 16/09/2018.
 */
object DateUtil {

    fun formatDate (calendar: Calendar) : String {
        var day = calendar.get(Calendar.DAY_OF_MONTH)
        var month = calendar.get(Calendar.MONTH)
        month = month+1
        var year = calendar.get(Calendar.YEAR)
        return "$day/$month/$year"
    }

    fun getToday() : Calendar{
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.HOUR_OF_DAY, 0)
        calendar.set(Calendar.MINUTE, 0)
        calendar.set(Calendar.SECOND, 0)
        calendar.set(Calendar.MILLISECOND, 0)
        return calendar
    }

    fun getDiffDays(calendar1: Calendar, calendar2: Calendar) : Int{
        val msDiff = calendar1.timeInMillis - calendar2.timeInMillis
        return TimeUnit.MILLISECONDS.toDays(msDiff).toInt()
    }
}