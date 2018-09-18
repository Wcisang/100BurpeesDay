package com.example.williamcisang.a100burpeesday.domain.database.dao

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.*
import com.example.williamcisang.a100burpeesday.domain.Session

/**
 * Created by WCisang on 15/09/2018.
 */

@Dao
interface SessionDAO {

    @Query("SELECT * FROM session WHERE state = 0 LIMIT 1")
    fun getActiveSession() : LiveData<Session>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun createSession(session: Session) : Long

    @Update
    fun updateSession(session: Session)
}