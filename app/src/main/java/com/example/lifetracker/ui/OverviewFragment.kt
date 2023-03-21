package com.example.lifetracker.ui

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.lifetracker.data.TaskTemplate
import com.example.myapplication.R
import com.google.android.material.progressindicator.CircularProgressIndicator

class OverviewFragment : Fragment(R.layout.overview_fragment) {
    private val TAG = "OverviewFragment"

    // ViewModel and adapter containers
    private val viewModel: TaskViewModel by viewModels()
    private val taskAdapter = TaskAdapter(::onTaskItemClick)

    // RecyclerView container
    private lateinit var taskListRV: RecyclerView


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Set up RecyclerView.
        taskListRV = view.findViewById(R.id.rv_task_list)
        taskListRV.layoutManager = LinearLayoutManager(requireContext())
        taskListRV.setHasFixedSize(true)
        taskListRV.adapter = taskAdapter


        // Set observer for task data. If the taskTemplates list is not null, pass to UI
        viewModel.taskTemplates.observe(viewLifecycleOwner) { taskTemplates ->
            taskAdapter.updateTaskTemplates(taskTemplates)
            taskListRV.visibility = View.VISIBLE
            taskListRV.scrollToPosition(0)
            // supportActionBar?.title = forecast.city.name
        }

        // Set observer for record data. If the taskTemplates list is not null, pass to UI
        viewModel.taskRecords.observe(viewLifecycleOwner) { taskRecords ->
            taskAdapter.updateTaskRecords(taskRecords)
        }
    }
    // Function called when task is clicked on from overview
    private fun onTaskItemClick(taskTemplate: TaskTemplate) {
        Log.d(TAG, "onTaskItemClick() called, task: $taskTemplate")
        val directions = OverviewFragmentDirections.navigateToTask(taskTemplate)
        findNavController().navigate(directions)
    }
}