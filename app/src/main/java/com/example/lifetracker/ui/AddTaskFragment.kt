package com.example.lifetracker.ui

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.lifetracker.data.Task
import com.example.lifetracker.data.TaskTemplate
import com.example.myapplication.R

class AddTaskFragment : Fragment(R.layout.add_task_fragment){
    // Instantiate viewModel and adapter
    private val viewModel: TaskViewModel by viewModels()

    // Grab hooks for views
    private lateinit var nameTV : TextView
    private lateinit var nameET : EditText
    private lateinit var goalTV : TextView
    private lateinit var goalET : EditText
    private lateinit var submitBtn : Button

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        // Required Magic
        super.onViewCreated(view, savedInstanceState)

        // Find views
        nameTV = view.findViewById(R.id.add_task_name_title)
        nameET = view.findViewById(R.id.add_task_name_edit)
        goalTV = view.findViewById(R.id.add_task_goal_title)
        goalET = view.findViewById(R.id.add_task_goal_edit)
        submitBtn = view.findViewById(R.id.add_task_submit_button)

        submitBtn.setOnClickListener {
            val new_task = TaskTemplate(
                nameET.text.toString(),
                1,
                true,
                Integer.parseInt(goalET.text.toString())
            )
            viewModel.addTaskTemplate(new_task)
            val directions = AddTaskFragmentDirections.navigateToOverview()
            findNavController().navigate(directions)
        }


    }

}