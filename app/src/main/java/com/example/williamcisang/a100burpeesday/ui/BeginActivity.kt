package com.example.williamcisang.a100burpeesday.ui

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.williamcisang.a100burpeesday.R
import android.app.DatePickerDialog
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.databinding.DataBindingUtil
import android.widget.Toast
import com.example.williamcisang.a100burpeesday.databinding.ActivityBeginBinding
import com.example.williamcisang.a100burpeesday.domain.Session
import com.example.williamcisang.a100burpeesday.util.DateUtil
import com.example.williamcisang.a100burpeesday.viewmodel.BeginViewModel
import java.util.Calendar


class BeginActivity : AppCompatActivity() {

    lateinit var binding: ActivityBeginBinding
    lateinit var viewmodel: BeginViewModel
    private var calendar : Calendar = Calendar.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewmodel = ViewModelProviders.of(this).get(BeginViewModel::class.java)
        binding =  DataBindingUtil.setContentView(this, R.layout.activity_begin)
        setupCalendar()
        registerListeners()
        registerObservers()
        setupEditTextTodayDate()
    }

    private fun setupEditTextTodayDate() {
        binding.edBeginDate.setText(DateUtil.formatDate(calendar))
    }

    private fun registerListeners() {
        binding.edBeginDate.setOnClickListener { dateDialog() }
        binding.btBeginDate.setOnClickListener { confirmDate() }
    }

    private fun confirmDate() {
        val today = DateUtil.getToday()
        val daysDiff = DateUtil.getDiffDays(today, calendar)
        if (daysDiff > 90) {
            Toast.makeText(this, "Escolha uma data mais próxima, por favor.", Toast.LENGTH_LONG).show()
            return
        }else if (daysDiff < 0) {
            Toast.makeText(this, "Escolha uma data entre hoje e os dias anteriores.", Toast.LENGTH_LONG).show()
            return
        }
        viewmodel.createNewSession(calendar)
    }

    private fun registerObservers() {
        viewmodel.createResponse.observe(this, Observer { startMainActivity(it) })
    }

    private fun setupCalendar() {
        calendar.set(Calendar.HOUR_OF_DAY, 0)
        calendar.set(Calendar.MINUTE, 0)
        calendar.set(Calendar.SECOND, 0)
        calendar.set(Calendar.MILLISECOND, 0)
    }

    private fun startMainActivity(session: Session?) {
        val it = Intent(this, MainActivity::class.java)
        it.flags = Intent.FLAG_ACTIVITY_NEW_TASK and Intent.FLAG_ACTIVITY_CLEAR_TASK
        it.putExtra("session", session)
        startActivity(it)
    }

    fun dateDialog() {
        val listener = DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
            binding.edBeginDate.setText(dayOfMonth.toString() + "/" +(monthOfYear+1) + "/" + year)
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            calendar.set(Calendar.MONTH, monthOfYear)
            calendar.set(Calendar.YEAR, year)
        }
        val dpDialog = DatePickerDialog(this, listener, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH))
        dpDialog.show()
    }
}
