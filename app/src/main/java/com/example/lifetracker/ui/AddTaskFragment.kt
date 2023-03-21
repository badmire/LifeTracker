package com.example.lifetracker.ui

import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.example.myapplication.R

class AddTaskFragment : Fragment(R.layout.add_task_fragment){
    // Instantiate viewModel and adapter
    private val viewModel: TaskViewModel by viewModels()

}