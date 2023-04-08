package ru.malkollm.school_android.api

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import ru.malkollm.school_android.databinding.ItemScheduleBinding
import ru.malkollm.school_android.models.Lesson
import ru.malkollm.school_android.models.Schedule

class ScheduleDatesAdapter : RecyclerView.Adapter<ScheduleDatesAdapter.ScheduleViewHolder>() {
    inner class ScheduleViewHolder(val binding: ItemScheduleBinding) : RecyclerView.ViewHolder(binding.root)

    private val diffCallback = object : DiffUtil.ItemCallback<Schedule>(){
        override fun areItemsTheSame(oldItem: Schedule, newItem: Schedule): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Schedule, newItem: Schedule): Boolean {
            return oldItem == newItem
        }
    }

    private val differ = AsyncListDiffer(this, diffCallback)
    var todos: List<Schedule>
        get() = differ.currentList
        set(value) {differ.submitList(value)}

    override fun getItemCount() = todos.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ScheduleDatesAdapter.ScheduleViewHolder {
        return ScheduleViewHolder(
            ItemScheduleBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        ))
    }

    override fun onBindViewHolder(holder: ScheduleDatesAdapter.ScheduleViewHolder, position: Int) {
        holder.binding.apply {
            val todo = todos[position]
            val numberInc = position + 1
            tvNumber.text = numberInc.toString()
            tvNameLesson.text = todo.lessonName
            val teacherFIO = todo.lastName + ' ' + todo.firstName + ' ' + todo.middleName
            tvTeacher.text = teacherFIO
            val lessonTime = todo.lessonStart + " - " + todo.lessonEnd
            tvLessonTime.text = lessonTime
        }
    }
}