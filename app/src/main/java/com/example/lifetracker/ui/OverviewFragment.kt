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
    // Magic from Hess
    private val TAG = "OverviewFragment"

    // Instatiate viewModel and adapter
    private val viewModel: TaskViewModel by viewModels()
    private val taskAdapter = TaskAdapter(::onTaskItemClick)

    // Instantiate reference for views from the layout
    private lateinit var taskListRV: RecyclerView
    private lateinit var loadingErrorTV: TextView
    private lateinit var loadingIndicator: CircularProgressIndicator

    // Initialization code
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        // Required magic
        super.onViewCreated(view, savedInstanceState)

        // Fetch views for the loading indicator and error message
        loadingErrorTV = view.findViewById(R.id.tv_loading_error)
        loadingIndicator = view.findViewById(R.id.loading_indicator)

        // Fetch and configure task recycler view
        taskListRV = view.findViewById(R.id.rv_task_list)
        taskListRV.layoutManager = LinearLayoutManager(requireContext())
        taskListRV.setHasFixedSize(true)
        taskListRV.adapter = taskAdapter

        // Set observer for task list in viewModel
        viewModel.taskTemplates.observe(viewLifecycleOwner) { taskTemplates ->
            if (taskTemplates != null) { // Check for empty list
                taskAdapter.updateTaskTemplates(taskTemplates) // Update UI with new data
                taskListRV.visibility = View.VISIBLE // Ensure RV is visible after loading/error shennanigans
                taskListRV.scrollToPosition(0) // Scroll back to the top
            }
        }

        // Set observer for records list in viewModel
        // Needed to set the "last done" stamp
        viewModel.taskRecords.observe(viewLifecycleOwner) { taskRecords ->
            if (taskRecords != null) {
                taskAdapter.updateTaskRecords(taskRecords)
            }
        }

        // Set observer for API error
        viewModel.error.observe(viewLifecycleOwner) { error ->
            if (error != null) {
                loadingErrorTV.text = getString(R.string.loading_error, error.message)
                loadingErrorTV.visibility = View.VISIBLE
                Log.e(TAG, "Error fetching forecast: ${error.message}")
            }
        }

        /*
         * Set up an observer on the loading status of the API query.  Display the correct UI
         * elements based on the current loading status.
         */
        viewModel.loading.observe(viewLifecycleOwner) { loading ->
            if (loading) {
                loadingIndicator.visibility = View.VISIBLE
                loadingErrorTV.visibility = View.INVISIBLE
                taskListRV.visibility = View.INVISIBLE
            } else {
                loadingIndicator.visibility = View.INVISIBLE
            }
        }
//        viewModel.debugHardcode()
    }

    override fun onResume() {
        super.onResume()
        // TODO: Settings stuff goes here
        // val sharedPrefs = PreferenceManager.getDefaultSharedPreferences(requireContext())
        // val city = sharedPrefs.getString(getString(R.string.pref_city_key), "Corvallis,OR,US")
        // val units = sharedPrefs.getString(getString(R.string.pref_units_key), null)

        // This is the actual call that kicks off all of the database operations and updating the UI
        // actually, this does nothing atm. I don't think it is neccesary??
        viewModel.loadTasks()
    }

    // Onclick function for each of the RecyclerView cards
    // Navigates into the task detail view for the selected task.
    private fun onTaskItemClick(taskTemplate: TaskTemplate) {
        Log.d(TAG, "onTaskItemClick() called, task: $taskTemplate")
        val directions = OverviewFragmentDirections.navigateToTask(taskTemplate)
        findNavController().navigate(directions)
        //val directions = OverviewFragmentDirections.navigateToForecastDetail(forecastPeriod, forecastCity = forecastAdapter.forecastCity!!)
        //findNavController().navigate(directions)
    }
}