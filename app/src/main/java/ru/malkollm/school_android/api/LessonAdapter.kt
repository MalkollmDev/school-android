package ru.malkollm.school_android.api

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import ru.malkollm.school_android.databinding.ItemLessonBinding

//class LessonAdapter : RecyclerView.Adapter<LessonAdapter.TodoViewHolder>() {
//    inner class TodoViewHolder(val binding: ItemLessonBinding) : RecyclerView.ViewHolder(binding.root)
//
////    private val diffCallback = object : DiffUtil.ItemCallback<LessonItem>(){
////        override fun areItemsTheSame(oldItem: LessonItem, newItem: LessonItem): Boolean {
////            return oldItem.id == newItem.id
////        }
////
////        override fun areContentsTheSame(oldItem: LessonItem, newItem: LessonItem): Boolean {
////            return oldItem == newItem
////        }
////    }
////
////    private val differ = AsyncListDiffer(this, diffCallback)
////    var todos: List<LessonItem>
////        get() = differ.currentList
////        set(value) {differ.submitList(value)}
////
////    override fun getItemCount() = todos.size
////
////    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder {
////        return TodoViewHolder(ItemLessonBinding.inflate(
////            LayoutInflater.from(parent.context),
////            parent,
////            false
////        ))
////    }
////
////    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {
////        holder.binding.apply {
////            val todo = todos[position]
////            tvTitleLesson.text = todo.name
////        }
////    }
//}