package com.example.williamcisang.a100burpeesday.adapter

import android.graphics.Color
import android.support.v7.widget.RecyclerView
import android.text.format.DateUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.williamcisang.a100burpeesday.R
import com.example.williamcisang.a100burpeesday.domain.Session
import com.example.williamcisang.a100burpeesday.util.BurpeeCalculator
import java.util.*

/**
 * Created by WCisang on 15/09/2018.
 */
class ListDaysAdapter(var session: Session, var listener:  (Int) -> Unit) : RecyclerView.Adapter<ListDaysAdapter.DaysViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DaysViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_days, parent, false)
        return DaysViewHolder(view)
    }

    override fun getItemCount(): Int {
        return 100
    }

    override fun onBindViewHolder(holder: DaysViewHolder, position: Int) {
        val textView = holder.itemView.findViewById<TextView>(R.id.tvItemDays)
        textView.text = "" + (position + 1)
        setBackground(textView, position)
        holder.itemView.setOnClickListener { listener(position) }
    }

    private fun setBackground(textView: TextView, position: Int) {
        val itemDate = Calendar.getInstance()
        itemDate.time = session.beginDate
        itemDate.add(Calendar.DAY_OF_MONTH, position)

        if (DateUtils.isToday(itemDate.timeInMillis)) {
            textView.setBackgroundResource(R.drawable.mini_circle_today)
        } else if (itemDate.before(Calendar.getInstance())) {
            if (BurpeeCalculator.isLate(position + 1, session.completedBurpees)) {
                textView.setBackgroundResource(R.drawable.mini_circle_incompleted)
            } else {
                textView.setBackgroundResource(R.drawable.mini_circle_completed)
            }
        } else {
            textView.setBackgroundResource(R.drawable.mini_circle_default)
        }
    }

    class DaysViewHolder(view: View) : RecyclerView.ViewHolder(view) {}
}