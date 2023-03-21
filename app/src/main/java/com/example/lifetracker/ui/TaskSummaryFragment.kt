package com.example.lifetracker.ui

import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.example.myapplication.R

class TaskSummaryFragment : Fragment(R.layout.task_summary_fragment) {
    // Instantiate viewModel and adapter
    private val viewModel: TaskViewModel by viewModels()

    // get hooks for passed args
//    private val args: TaskSummaryFragmentArgs by navArgs()
}