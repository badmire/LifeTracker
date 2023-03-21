package com.example.lifetracker.ui

import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.example.myapplication.R

class TaskSettingsFragment : Fragment(R.layout.task_settings_fragment) {
    // Instantiate viewModel and adapter
    private val viewModel: TaskViewModel by viewModels()

    // get hooks for passed args
    private val args: TaskSettingsFragmentArgs by navArgs()
}