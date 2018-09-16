package com.example.williamcisang.a100burpeesday.viewmodel

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.example.williamcisang.a100burpeesday.domain.Session
import com.example.williamcisang.a100burpeesday.domain.database.DatabaseManager
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

/**
 * Created by WCisang on 15/09/2018.
 */
class MainViewModel : ViewModel() {

    val sessionValue = MutableLiveData<Session>()

    fun updateSession(session: Session) {

        doAsync {
            DatabaseManager.getSessionDao().updateSession(session)
            uiThread {
                sessionValue.value = session
            }
        }
    }


}