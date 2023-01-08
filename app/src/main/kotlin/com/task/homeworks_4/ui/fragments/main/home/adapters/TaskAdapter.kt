package com.task.homeworks_4.ui.fragments.main.home.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.task.homeworks_4.R
import com.task.homeworks_4.databinding.ItemTaskBinding
import com.task.homeworks_4.ui.models.Task

class TaskAdapter(
    val onLongClick: (task: Task, position: Int) -> Unit,
    val context: Context
) : Adapter<TaskAdapter.TasksViewHolder>() {
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

    fun deleteItem(position: Int) {
        list.removeAt(position)
        notifyItemRemoved(position)
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
            if (adapterPosition % 2 == 0) {
                binding.root.setBackgroundColor(ContextCompat.getColor(context, R.color.black))
                binding.tvTaskDescription.setTextColor(
                    ContextCompat.getColor(
                        context,
                        R.color.white
                    )
                )
                binding.tvTaskTitle.setTextColor(ContextCompat.getColor(context, R.color.white))

            } else {
                binding.root.setBackgroundColor(ContextCompat.getColor(context, R.color.white))
                binding.tvTaskDescription.setTextColor(
                    ContextCompat.getColor(
                        context,
                        R.color.black
                    )
                )
                binding.tvTaskTitle.setTextColor(ContextCompat.getColor(context, R.color.black))
            }


        }
    }
}