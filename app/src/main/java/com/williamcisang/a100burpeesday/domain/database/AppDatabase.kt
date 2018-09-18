package com.example.williamcisang.a100burpeesday.domain.database

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.TypeConverters
import com.example.williamcisang.a100burpeesday.domain.Session
import com.example.williamcisang.a100burpeesday.domain.database.dao.SessionDAO
import com.example.williamcisang.a100burpeesday.util.TypeDatabaseConverter

/**
 * Created by WCisang on 15/09/2018.
 */
@Database(entities = arrayOf(Session::class), version = 1)
@TypeConverters(TypeDatabaseConverter::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun getSessionDao() : SessionDAO
}