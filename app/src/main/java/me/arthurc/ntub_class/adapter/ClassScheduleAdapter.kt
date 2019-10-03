package me.arthurc.ntub_class.adapter

import android.content.Context
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import kotlinx.android.synthetic.main.item_class_schedule.view.*
import me.arthurc.ntub_class.R
import me.arthurc.ntub_class.model.ClassScheduleModel

class ClassScheduleAdapter(
    private val context: Context,
    private val data: ClassScheduleModel
) : RecyclerView.Adapter<ClassScheduleAdapter.ViewHolder>() {

    private val weekDay = listOf("星期一", "星期二", "星期三", "星期四", "星期五", "星期六", "星期日")

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ClassScheduleAdapter.ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_class_schedule, parent, false))
    }

    override fun getItemCount(): Int {
        return data.klass.size
    }

    override fun onBindViewHolder(holder: ClassScheduleAdapter.ViewHolder, position: Int) {
        val targets: MutableList<Int> = mutableListOf()

        data.klass[position].forEachIndexed { i, c -> if (c != null) targets.add(i) }
        holder.itemView.classList.layoutManager = LinearLayoutManager(context)
        holder.itemView.classList.adapter = ClassAdapter(context, targets, data.klass[position], data.time)

        holder.day.text = weekDay[position]
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val day: TextView = itemView.findViewById(R.id.day)
        val classList: RecyclerView = itemView.findViewById(R.id.classList)
    }
}