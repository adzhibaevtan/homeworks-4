package com.task.homework_4.ui.home

import android.app.AlertDialog
import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.task.homework_4.App
import com.task.homework_4.R
import com.task.homework_4.databinding.FragmentHomeBinding
import com.task.homework_4.ui.home.adapters.TaskAdapter
import com.task.homework_4.ui.models.Task

class HomeFragment : Fragment(R.layout.fragment_home) {
    private val binding by viewBinding(FragmentHomeBinding::bind)
    private lateinit var taskAdapter : TaskAdapter
    private val args by navArgs<HomeFragmentArgs>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navigateToCreatingNewTask()
        constructRecycler()
        insertNewTask()
    }

    private fun constructRecycler() {
       val result =  App.db.dao().query()
          taskAdapter = TaskAdapter(this::onLongClick,requireContext())
        taskAdapter.addTasks(result)
        binding.rvTasks.adapter = taskAdapter
        binding.rvTasks.layoutManager = LinearLayoutManager(requireContext())
    }

    private fun navigateToCreatingNewTask() {
        binding.fabCreateTask.setOnClickListener {
            findNavController().navigate(
                HomeFragmentDirections.actionNavigationHomeToCreateNewTaskFragment(
                    taskAdapter.getCurrentList().toTypedArray()
                )
            )
        }
    }

    private fun insertNewTask() {
        args.task?.let {
            it.forEach { task ->
                taskAdapter.addTask(task)

            }
        }
    }

    private fun onLongClick(task: Task, position: Int){
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Delete task")
        builder.setMessage("Are you sure?")
        builder.setPositiveButton(android.R.string.yes) { _, _ ->
            App.db.dao().delete(task)
            taskAdapter.deleteItem(position)
        }
        builder.setNegativeButton(android.R.string.no){ _, _ ->

        }
        builder.show()
    }
}