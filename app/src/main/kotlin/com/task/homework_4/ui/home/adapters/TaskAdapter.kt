package com.task.homework_4.ui.home.adapters

import android.icu.text.Transliterator
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.task.homework_4.databinding.ItemTaskBinding
import com.task.homework_4.ui.models.Task

class TaskAdapter(
    val onLongClick: (task: Task, position: Int) -> Unit,
) : Adapter<TaskAdapter.TasksViewHolder>()

 {
    private val list = arrayListOf<Task>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = TasksViewHolder(
        ItemTaskBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: TasksViewHolder, position: Int) {
        holder.onBind(list[position])

    }

    override fun getItemCount() = list.size

    fun addTask(task: Task) {
        list.add(task)
        notifyItemInserted(list.lastIndex)
    }

     fun deleteItem(position: Int){
         list.removeAt(position)
         notifyDataSetChanged()
     }

    fun addTasks(task: List<Task>) {
        this.list.clear()
        this.list.addAll(task)
        notifyItemInserted(list.lastIndex)
    }

    fun getCurrentList() = list

    inner class TasksViewHolder(private val binding: ItemTaskBinding) : ViewHolder(binding.root) {
        fun onBind(task: Task) {
            binding.tvTaskTitle.text = task.title
            binding.tvTaskDescription.text = task.description
            itemView.setOnLongClickListener {
                onLongClick(task, absoluteAdapterPosition)
                return@setOnLongClickListener true
            }


        }
    }
}