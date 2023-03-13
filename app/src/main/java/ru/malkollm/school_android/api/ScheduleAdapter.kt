package ru.malkollm.school_android.api

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ru.malkollm.school_android.R
import ru.malkollm.school_android.models.Lesson

class ScheduleAdapter(var lessonsList: ArrayList<Lesson>?, var itemClick: lessonClickListener) : RecyclerView.Adapter<ScheduleAdapter.RecyclerViewHolder>() {
    override fun getItemCount(): Int {
        return lessonsList!!.size
    }

    interface lessonClickListener {
        fun getItem(position: Int)
    }

    override fun onBindViewHolder(holder: RecyclerViewHolder, position: Int) {
        holder.bindData(lessonsList, position)
    }

//    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): RecyclerViewHolder {
//        var view: View = LayoutInflater.from(parent!!.context).inflate(R.layout.item_lesson, parent, false)
//        return RecyclerViewHolder(view, itemClick)
//    }

    class RecyclerViewHolder(itemView: View, var itemClick: lessonClickListener) : RecyclerView.ViewHolder(itemView) {
        //    class RecyclerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var textName: TextView = itemView.findViewById(R.id.tvTitle)
        fun bindData(lessonsList: ArrayList<Lesson>?, position: Int) {
            textName.text = lessonsList!!.get(position).name
            itemView.setOnClickListener(View.OnClickListener {
                itemClick.getItem(adapterPosition)
            })
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.item_lesson, parent, false)
        return RecyclerViewHolder(view, itemClick)
    }
}