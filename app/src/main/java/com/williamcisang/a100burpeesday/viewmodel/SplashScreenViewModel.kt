package com.example.williamcisang.a100burpeesday.viewmodel

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import com.example.williamcisang.a100burpeesday.domain.Session
import com.example.williamcisang.a100burpeesday.domain.database.DatabaseManager

/**
 * Created by WCisang on 15/09/2018.
 */
class SplashScreenViewModel(application: Application) : AndroidViewModel(application) {

    lateinit var sessionData : LiveData<Session>

    init {
        searchOnProgressSession()
    }

    private fun searchOnProgressSession() {
        sessionData = DatabaseManager.getSessionDao().getActiveSession()
    }
}