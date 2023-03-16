package ru.malkollm.school_android.api

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import ru.malkollm.school_android.databinding.ItemHomeworkBinding
import ru.malkollm.school_android.databinding.ItemLessonBinding
import ru.malkollm.school_android.models.Homework

class HomeworkAdapter : RecyclerView.Adapter<HomeworkAdapter.TodoViewHolder>() {
    inner class TodoViewHolder(val binding: ItemHomeworkBinding) : RecyclerView.ViewHolder(binding.root)

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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder {
        return TodoViewHolder(ItemHomeworkBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        ))
    }

    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {
        holder.binding.apply {
            val todo = todos[position]
            tvNLessonHomework.text = todo.lessonName
            tvGroupNumber.text = todo.numberGroup.toString()
            tvTextHomework.text = todo.task
            tvDateEnd.text = todo.date
        }
    }
}