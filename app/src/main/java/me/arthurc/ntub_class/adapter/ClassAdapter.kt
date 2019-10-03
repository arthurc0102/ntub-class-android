package me.arthurc.ntub_class.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import me.arthurc.ntub_class.R
import me.arthurc.ntub_class.model.ClassModel
import me.arthurc.ntub_class.model.TimeModel

class ClassAdapter(
    private val context: Context,
    private val targets: List<Int>,
    private val data: List<ClassModel?>,
    private val times: List<TimeModel>
) : RecyclerView.Adapter<ClassAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ClassAdapter.ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_class, parent, false))
    }

    override fun getItemCount(): Int {
        return targets.size
    }

    override fun onBindViewHolder(holder: ClassAdapter.ViewHolder, position: Int) {
        val index = targets[position]
        val klass = data[index] ?: return
        val time = times[index]

        holder.classNo.text = time.classNo
        holder.startAt.text = time.startAt
        holder.endAt.text = time.endAt
        holder.name.text = klass.name
        holder.teacher.text = klass.teacher
        holder.room.text = klass.room

    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val name: TextView = itemView.findViewById(R.id.name)
        val teacher: TextView = itemView.findViewById(R.id.teacher)
        val room: TextView = itemView.findViewById(R.id.room)
        val classNo: TextView = itemView.findViewById(R.id.classNo)
        val startAt: TextView = itemView.findViewById(R.id.startAt)
        val endAt: TextView = itemView.findViewById(R.id.endAt)
    }

}