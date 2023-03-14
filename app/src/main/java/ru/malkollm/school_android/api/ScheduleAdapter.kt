package ru.malkollm.school_android.api

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import ru.malkollm.school_android.databinding.ItemScheduleBinding
import ru.malkollm.school_android.models.schedule.ScheduleItem

class ScheduleAdapter : RecyclerView.Adapter<ScheduleAdapter.TodoViewHolder>() {
    inner class TodoViewHolder(val binding: ItemScheduleBinding) : RecyclerView.ViewHolder(binding.root)

    private val diffCallback = object : DiffUtil.ItemCallback<ScheduleItem>(){
        override fun areItemsTheSame(oldItem: ScheduleItem, newItem: ScheduleItem): Boolean {
            return oldItem.numberGroup == newItem.numberGroup
        }

        override fun areContentsTheSame(oldItem: ScheduleItem, newItem: ScheduleItem): Boolean {
            return oldItem == newItem
        }
    }

    private val differ = AsyncListDiffer(this, diffCallback)
    var todos: List<ScheduleItem>
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
            tvNameLesson.text = todo.lessonItems[0].lessonName
        }
    }
}