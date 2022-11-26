package com.task.homework_4.ui.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.task.homework_4.R
import com.task.homework_4.databinding.FragmentHomeBinding
import com.task.homework_4.ui.home.adapters.TaskAdapter
import com.task.homework_4.ui.models.Task

class HomeFragment : Fragment(R.layout.fragment_home) {
    private val binding by viewBinding(FragmentHomeBinding::bind)
    private val taskAdapter = TaskAdapter()
    private val args by navArgs<HomeFragmentArgs>()
    private var lastInsertedTask: Task? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navigateToCreatingNewTask()
        constructRecycler()
        insertNewTask()
    }

    private fun constructRecycler() {
        binding.rvTasks.adapter = taskAdapter
        binding.rvTasks.layoutManager = LinearLayoutManager(requireContext())
    }

    private fun navigateToCreatingNewTask() {
        binding.fabCreateTask.setOnClickListener {
            findNavController().navigate(R.id.action_navigation_home_to_createNewTaskFragment)
        }
    }

    private fun insertNewTask() {
        if (lastInsertedTask != args.task)
            args.task?.let {
                taskAdapter.addTask(it)

                lastInsertedTask?.let { it1 -> taskAdapter.addTask(it1) }
            }
        lastInsertedTask = args.task
    }
}