package com.example.williamcisang.a100burpeesday.domain.database

import android.arch.persistence.room.Room
import com.example.williamcisang.a100burpeesday.AppApplication
import com.example.williamcisang.a100burpeesday.domain.database.dao.SessionDAO

/**
 * Created by WCisang on 15/09/2018.
 */

object DatabaseManager {

    private var dbInstance: AppDatabase
    init {
        val appContext = AppApplication.getInstance().applicationContext
        dbInstance = Room.databaseBuilder(
                appContext,
                AppDatabase::class.java,
                "100burpee.sqlite"
        ).build()
    }

    fun getSessionDao() : SessionDAO {
        return dbInstance.getSessionDao()
    }
}