package com.example.williamcisang.a100burpeesday.ui.fragment

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

import com.example.williamcisang.a100burpeesday.R
import com.example.williamcisang.a100burpeesday.databinding.FragmentDetailBinding
import com.example.williamcisang.a100burpeesday.domain.Day
import com.example.williamcisang.a100burpeesday.ui.MainActivity
import com.example.williamcisang.a100burpeesday.util.DateUtil
import android.content.DialogInterface
import android.support.v4.widget.TextViewCompat
import android.support.v7.app.AlertDialog
import android.text.Editable
import android.text.InputType
import android.util.TypedValue
import android.widget.EditText
import android.widget.Toast
import com.example.williamcisang.a100burpeesday.R.id.btComplete
import com.example.williamcisang.a100burpeesday.R.id.btHalf
import com.example.williamcisang.a100burpeesday.util.BurpeeCalculator
import kotlinx.android.synthetic.main.fragment_detail.*


class DetailFragment : Fragment() {

    lateinit var tvDay : TextView
    lateinit var tvBurpee : TextView
    lateinit var binding: FragmentDetailBinding
    var day: Day? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater,
                R.layout.fragment_detail, container, false)
        TextViewCompat.setAutoSizeTextTypeWithDefaults(binding.tvBurpee, TextViewCompat.AUTO_SIZE_TEXT_TYPE_UNIFORM)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        registerListeners()
    }

    private fun registerListeners() {
        binding.btComplete.setOnClickListener { showCompletedConfimationDialog() }
        binding.btHalf.setOnClickListener { parcialBurpees() }
    }

    private fun showCompletedConfimationDialog() {
            val alert = AlertDialog.Builder(activity!!)
            alert.setMessage(null)
            alert.setTitle(R.string.confirm_save_burpee_message)

            alert.setPositiveButton(R.string.confirm_action, DialogInterface.OnClickListener { dialog, whichButton ->
                completeBurpees()
                dialog.dismiss()
            })

            alert.setNegativeButton(R.string.cancel_action, DialogInterface.OnClickListener { dialog, whichButton ->
                dialog.dismiss()
            })

            alert.show()
    }

    private fun completeBurpees() {
        (activity as MainActivity).updateSession(day?.total!!)
    }

    private fun parcialBurpees() {
        showPartialDialog()
    }


    fun refreshView(day: Day) {
        this.day = day
        binding.day = day
        binding.tvDay.text = DateUtil.formatDate(day.calendar)
        configButtons()
        if(day.total==0) {
            binding.tvBurpee.setBackgroundResource(R.drawable.completed_burpee_background)

        } else {
            binding.tvBurpee.setBackgroundResource(R.drawable.main_burpee_circle)
        }

        binding.executePendingBindings()
    }

    private fun configButtons() {
        val today = DateUtil.getToday()
        if (today.before(day?.calendar) || day?.total == 0){
            btComplete.isEnabled = false
            btHalf.isEnabled = false
        }else{
            btComplete.isEnabled = true
            btHalf.isEnabled = true
        }
        if (day?.total == 1) {
            btHalf.isEnabled = false
        }
    }

    fun showPartialDialog() {
        val alert = AlertDialog.Builder(activity!!)
        val edittext = EditText(activity)
        edittext.inputType = InputType.TYPE_CLASS_NUMBER
        val maxBurpee = BurpeeCalculator.getMissingBurpees(day!!.day, (activity as MainActivity).session.completedBurpees) - 1
        alert.setMessage("Digite um valor entre 1 e $maxBurpee")
        alert.setTitle("Burpees realizados")

        alert.setView(edittext)

        alert.setPositiveButton(R.string.confirm_action, DialogInterface.OnClickListener { dialog, whichButton ->
            val text = edittext.text.toString()
            validatePartial(text)
            dialog.dismiss()
        })

        alert.setNegativeButton(R.string.cancel_action, DialogInterface.OnClickListener { dialog, whichButton ->
            dialog.dismiss()
        })

        alert.show()
    }

    private fun validatePartial(value: String) {
        val maxBurpee = BurpeeCalculator.getMissingBurpees(day!!.day, (activity as MainActivity).session.completedBurpees) - 1
        var intvalue = 0
        try {
            intvalue = Integer.valueOf(value)
        }catch (e: Exception){
            Toast.makeText(activity, "Valor inválido", Toast.LENGTH_SHORT).show()
            return
        }

        if (intvalue == 0 || intvalue > maxBurpee ) {
            Toast.makeText(activity, "Número inválido. Por favor, escolha entre 1 e $maxBurpee.", Toast.LENGTH_LONG).show()
            return
        }
        (activity as MainActivity).updateSession(intvalue)
    }
}
