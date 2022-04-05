package com.example.todolist.ui.tasks

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.todolist.data.Task
import com.example.todolist.databinding.ItemTaskBinding

class TasksAdapter(private val listener: OnItemClickListener) : ListAdapter<Task, TasksAdapter.TasksViewHolder>(DiffCallback()) {

    inner class TasksViewHolder(private val binding: ItemTaskBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.apply {
                root.setOnClickListener {
                    if (adapterPosition != RecyclerView.NO_POSITION){
                        val task = getItem(adapterPosition)
                        listener.onItemClick(task)
                    }
                }
                checkboxCompleted.setOnClickListener {
                    if (adapterPosition != RecyclerView.NO_POSITION){
                        val task = getItem(adapterPosition)
                        listener.onCheckBoxClick(task, checkboxCompleted.isChecked)
                    }
                }
            }
        }

        fun bind(task: Task) {
            binding.apply {
                checkboxCompleted.isChecked = task.completed
                tvTaskName.text = task.name
                tvTaskName.paint.isStrikeThruText = task.completed
                ivLabelPriority.isVisible = task.important
            }
        }
    }

    interface OnItemClickListener{
        fun onItemClick(task: Task)
        fun onCheckBoxClick(task: Task, isChecked: Boolean)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TasksViewHolder {
        val binding = ItemTaskBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TasksViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TasksViewHolder, position: Int) {
        val currentItem = getItem(position)
        holder.bind(currentItem)
    }

    class DiffCallback : DiffUtil.ItemCallback<Task>() {
        override fun areItemsTheSame(oldItem: Task, newItem: Task) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Task, newItem: Task) =
            oldItem == newItem
    }
}