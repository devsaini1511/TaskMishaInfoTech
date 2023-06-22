package com.example.taskmishainfotech.ui.viewdata

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.taskmishainfotech.databinding.HolderItemLayoutBinding
import com.example.taskmishainfotech.domain.TaskData

class StoreListAdapter(
    val listener: (TaskData, Int) -> Unit,
) : ListAdapter<TaskData, StoreListAdapter.TaskListItemHolder>(DIFF_CALLBACK) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskListItemHolder {
        val optionBinding = HolderItemLayoutBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return TaskListItemHolder(optionBinding)
    }

    override fun onBindViewHolder(holder: TaskListItemHolder, position: Int) {
        with(getItem(position)) {
            holder.bind(this) { listener(this, position) }
        }
    }

    inner class TaskListItemHolder(private val itemBinding: HolderItemLayoutBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {

        fun bind(data: TaskData, listener: () -> Unit) {
            itemBinding.checkBox.text = data.title
            itemBinding.checkBox.apply {
                text = data.title
                setOnCheckedChangeListener(null) // Clear previous listener to avoid recursion

                paintFlags = if (isChecked) {
                    paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
                } else {
                    paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
                }
                setOnCheckedChangeListener { _, isChecked ->
                    listener()

                    paintFlags = if (isChecked) {
                        paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
                    } else {
                        paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
                    }
                }


            }
        }
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<TaskData>() {
            override fun areItemsTheSame(
                oldItem: TaskData,
                newItem: TaskData,
            ): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: TaskData,
                newItem: TaskData,
            ): Boolean {
                return oldItem.timestamp == newItem.timestamp
            }

        }
    }

}