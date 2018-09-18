package com.example.williamcisang.a100burpeesday.viewmodel

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import com.example.williamcisang.a100burpeesday.domain.Session
import com.example.williamcisang.a100burpeesday.domain.SessionState
import com.example.williamcisang.a100burpeesday.domain.database.DatabaseManager
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import java.util.*

/**
 * Created by WCisang on 15/09/2018.
 */

class BeginViewModel(application: Application) : AndroidViewModel(application) {

    var createResponse : MutableLiveData<Session> = MutableLiveData()

    fun createNewSession(calendar: Calendar) {
        val session = createSessionObject(calendar)
        doAsync {
            session.id = DatabaseManager.getSessionDao().createSession(session)
            uiThread {
                createResponse.value = session
            }
        }
    }

    private fun createSessionObject(calendar: Calendar) : Session {
        val session = Session()
        session.beginDate = calendar.time
        session.endDate = getDate100Days(calendar)
        session.totalBurpees = 5050
        session.completedBurpees = 0
        session.sessionState = createOnProgressState()
        return session
    }

    private fun getDate100Days(calendar: Calendar) : Date{
        calendar.add(Calendar.DAY_OF_MONTH, 99)
        return calendar.time
    }

    private fun createOnProgressState() : SessionState {
        val state = SessionState()
        state.state = SessionState.ON_PROGRESS
        return state
    }
}