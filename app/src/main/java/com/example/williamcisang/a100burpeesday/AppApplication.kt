package com.example.williamcisang.a100burpeesday

import android.app.Application

/**
 * Created by WCisang on 15/09/2018.
 */
class AppApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        appInstance = this
    }

    companion object {
        private var appInstance: AppApplication? = null
        fun getInstance(): AppApplication {
            if (appInstance == null)
                throw IllegalStateException("Configure a classe no manifest")
            return appInstance!!
        }
    }
}