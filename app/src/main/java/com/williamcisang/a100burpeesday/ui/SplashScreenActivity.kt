package com.example.williamcisang.a100burpeesday.ui

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_NEW_TASK
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.williamcisang.a100burpeesday.R
import com.example.williamcisang.a100burpeesday.domain.Session
import com.example.williamcisang.a100burpeesday.viewmodel.SplashScreenViewModel

class SplashScreenActivity : AppCompatActivity() {

    lateinit var viewmodel : SplashScreenViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        viewmodel = ViewModelProviders.of(this).get(SplashScreenViewModel::class.java)
        registerObserver()
    }

    private fun registerObserver() {
        viewmodel.sessionData.observe(this, Observer { sessionConfig(it) })
    }

    private fun sessionConfig(session: Session?) {
        if (session == null) startBeginActivity()
        else startMainActivity(session)
    }

    private fun startBeginActivity() {
        val it = Intent(this, BeginActivity::class.java)
        it.flags = Intent.FLAG_ACTIVITY_NEW_TASK and Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(it)
    }

    private fun startMainActivity(session: Session?){
        val it = Intent(this, MainActivity::class.java)
        it.flags = Intent.FLAG_ACTIVITY_NEW_TASK and Intent.FLAG_ACTIVITY_CLEAR_TASK
        it.putExtra("session", session)
        startActivity(it)
    }
}
