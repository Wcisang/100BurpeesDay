package com.example.williamcisang.a100burpeesday.util

import android.arch.persistence.room.TypeConverter
import java.util.*


/**
 * Created by WCisang on 15/09/2018.
 */
class TypeDatabaseConverter {

    @TypeConverter
    fun fromTimestamp(value: Long?): Date? {
        return if (value == null) null else Date(value)
    }

    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? {
        return date?.time
    }
}