package ru.malkollm.school_android.api

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import ru.malkollm.school_android.databinding.ItemScheduleBinding
import ru.malkollm.school_android.models.LessonItem

class ScheduleAdapter : RecyclerView.Adapter<ScheduleAdapter.TodoViewHolder>() {
    inner class TodoViewHolder(val binding: ItemScheduleBinding) : RecyclerView.ViewHolder(binding.root)

    private val diffCallback = object : DiffUtil.ItemCallback<LessonItem>(){
        override fun areItemsTheSame(oldItem: LessonItem, newItem: LessonItem): Boolean {
            return oldItem.lessonName == newItem.lessonName
        }

        override fun areContentsTheSame(oldItem: LessonItem, newItem: LessonItem): Boolean {
            return oldItem == newItem
        }
    }

    private val differ = AsyncListDiffer(this, diffCallback)
    var todos: List<LessonItem>
        get() = differ.currentList
        set(value) {differ.submitList(value)}

    override fun getItemCount() = todos.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ScheduleAdapter.TodoViewHolder {
        return TodoViewHolder(
            ItemScheduleBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        ))
    }

    override fun onBindViewHolder(holder: ScheduleAdapter.TodoViewHolder, position: Int) {
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