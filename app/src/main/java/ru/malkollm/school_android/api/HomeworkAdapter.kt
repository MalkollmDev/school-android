package ru.malkollm.school_android.api

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import ru.malkollm.school_android.databinding.ItemHomeworkBinding
import ru.malkollm.school_android.models.Homework
import java.text.SimpleDateFormat

class HomeworkAdapter : RecyclerView.Adapter<HomeworkAdapter.HomeworkViewHolder>() {
    inner class HomeworkViewHolder(val binding: ItemHomeworkBinding) : RecyclerView.ViewHolder(binding.root)

    private val diffCallback = object : DiffUtil.ItemCallback<Homework>(){
        override fun areItemsTheSame(oldItem: Homework, newItem: Homework): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Homework, newItem: Homework): Boolean {
            return oldItem == newItem
        }
    }

    private val differ = AsyncListDiffer(this, diffCallback)
    var todos: List<Homework>
        get() = differ.currentList
        set(value) {differ.submitList(value)}

    override fun getItemCount() = todos.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeworkViewHolder {
        return HomeworkViewHolder(ItemHomeworkBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        ))
    }

    override fun onBindViewHolder(holder: HomeworkViewHolder, position: Int) {
        val formatter = SimpleDateFormat("dd-MM-yyyy")
        holder.binding.apply {
            val todo = todos[position]
            tvNLessonHomework.text = todo.lessonName
            tvTextHomework.text = todo.task
            tvDateEnd.text = todo.date.substringBefore(delimiter = "T")
        }
    }
}