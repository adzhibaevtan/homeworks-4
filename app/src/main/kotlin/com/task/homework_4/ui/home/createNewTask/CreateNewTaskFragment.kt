package com.task.homework_4.ui.home.createNewTask

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.task.homework_4.App
import com.task.homework_4.R
import com.task.homework_4.databinding.FragmentCreateNewTaskBinding
import com.task.homework_4.ui.home.adapters.TaskAdapter
import com.task.homework_4.ui.models.Task

class CreateNewTaskFragment : Fragment(R.layout.fragment_create_new_task) {
    private val binding by viewBinding(FragmentCreateNewTaskBinding::bind)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        createNewTask()
    }

    private fun createNewTask() {
        binding.btnAddTask.setOnClickListener {
            if (binding.etTaskTitle.text.toString()
                    .isNotEmpty() || binding.etTaskDescription.text.toString().isNotEmpty()
            ) {
                findNavController().navigate(
                    CreateNewTaskFragmentDirections.actionCreateNewTaskFragmentToNavigationHome()
                )
                App.db.dao().insert(
                    Task(
                        binding.etTaskTitle.text.toString(),
                        binding.etTaskDescription.text.toString()
                    )
                )
            } else {

                binding.etTaskTitle.error = "Field is empty"
                binding.etTaskDescription.error = "Field is empty"
            }

        }
    }


}