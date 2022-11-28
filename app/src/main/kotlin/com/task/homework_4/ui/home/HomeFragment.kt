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

class HomeFragment : Fragment(R.layout.fragment_home) {
    private val binding by viewBinding(FragmentHomeBinding::bind)
    private val taskAdapter = TaskAdapter()
    private val args by navArgs<HomeFragmentArgs>()

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
}