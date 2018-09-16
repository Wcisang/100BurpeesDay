package com.example.williamcisang.a100burpeesday.ui

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.text.format.DateUtils
import android.view.ViewTreeObserver
import com.example.williamcisang.a100burpeesday.R
import com.example.williamcisang.a100burpeesday.adapter.ListDaysAdapter
import com.example.williamcisang.a100burpeesday.databinding.ActivityMainBinding
import com.example.williamcisang.a100burpeesday.domain.Day
import com.example.williamcisang.a100burpeesday.domain.Session
import com.example.williamcisang.a100burpeesday.ui.fragment.DetailFragment
import com.example.williamcisang.a100burpeesday.util.BurpeeCalculator
import com.example.williamcisang.a100burpeesday.util.DateUtil
import com.example.williamcisang.a100burpeesday.viewmodel.MainViewModel
import java.util.Calendar

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel
    lateinit var session: Session
    private lateinit var binding: ActivityMainBinding
    private lateinit var fragment: DetailFragment
    private var alreadyMove = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setIcon(R.mipmap.ic_launcher)
        supportActionBar?.title = getString(R.string.app_name).toLowerCase()
        fragment = DetailFragment()
        registerObserver()
        getSessionFromIntent()
        setupFragment()
    }

    override fun onResume() {
        super.onResume()
        alreadyMove = false
        setupList()
    }

    private fun registerObserver() {
        viewModel.sessionValue.observe(this, Observer {
            session = it!!
            setupList()
            itemClicked(getDay())
        })
    }

    private fun setupFragment() {
        supportFragmentManager
                .beginTransaction()
                .add(R.id.containerDetail, fragment)
                .commit()
    }

    private fun getSessionFromIntent() {
        if (intent == null || intent.extras == null) {
            finish()
            return
        }
        session = intent.extras.getParcelable("session") as Session
    }

    private fun setupList() {
        val manager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        binding.rvDays.layoutManager = manager
        binding.rvDays.adapter = ListDaysAdapter(session, this::itemClicked)
        binding.rvDays.viewTreeObserver
                .addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
                    override fun onGlobalLayout() {
                        if (!alreadyMove){
                            itemClicked(getDay())
                        }
                        binding.rvDays.smoothScrollToPosition(getDay())
                        alreadyMove = true
                        binding.rvDays.viewTreeObserver.removeOnGlobalLayoutListener(this)
                    }
                })
    }
    private fun itemClicked(position: Int) {
        val total = getTotalBurpees(position + 1)
        val day = Day(getDayCalendar(position), position + 1, total, createMessage(position+1, total))
        fragment.refreshView(day)
    }

    private fun createMessage(position: Int, total: Int): String {
        if ((total - position) > 0) {
            return "* $position (do dia) + " + (total - position) + " (dos dias anteriores)."
        }
        return ""
    }

    private fun getDay(): Int {
        if (fragment.day != null){
            return fragment.day!!.day -1
        }
        val calendar = Calendar.getInstance()
        calendar.time = session.beginDate
        val today : Calendar = DateUtil.getToday()
        if(today.after(session.endDate))
            calendar.time = session.endDate
        return DateUtil.getDiffDays(DateUtil.getToday(), calendar)
    }

    private fun getDayCalendar(position: Int): Calendar {
        val calendar = Calendar.getInstance()
        calendar.time = session.beginDate
        calendar.add(Calendar.DAY_OF_MONTH, position)
        return calendar
    }

    private fun getTotalBurpees(position: Int): Int {
        return BurpeeCalculator.getMissingBurpees(position, session.completedBurpees)
    }

    fun updateSession(burpees: Int) {
        session.completedBurpees = session.completedBurpees + burpees
        viewModel.updateSession(session)
    }


}
