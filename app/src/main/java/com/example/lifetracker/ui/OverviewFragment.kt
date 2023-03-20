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

    // Containers for UI views
    private lateinit var loadingErrorTV: TextView
    private lateinit var loadingIndicator: CircularProgressIndicator

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loadingErrorTV = view.findViewById(R.id.tv_loading_error)
        loadingIndicator = view.findViewById(R.id.loading_indicator)

        /*
         * Set up RecyclerView.
         */
        taskListRV = view.findViewById(R.id.rv_task_list)
        taskListRV.layoutManager = LinearLayoutManager(requireContext())
        taskListRV.setHasFixedSize(true)
        taskListRV.adapter = taskAdapter

        /*
         * Set up an observer on the current task data.  If the taskTemplates list is not null, display
         * it in the UI.
         */
        viewModel.taskTemplates.observe(viewLifecycleOwner) { taskTemplates ->
            taskAdapter.updateTaskTemplates(taskTemplates)
            taskListRV.visibility = View.VISIBLE
            taskListRV.scrollToPosition(0)
            // supportActionBar?.title = forecast.city.name
        }

        /*
         * Set up an observer on the most recent task records.  If any member of the taskRecords
         * list is not null, display it in the UI.
         */
        viewModel.taskRecords.observe(viewLifecycleOwner) { taskRecords ->
            taskAdapter.updateTaskRecords(taskRecords)
        }

        /*
         * Set up an observer on the error associated with the current API call.  If the error is
         * not null, display the error that occurred in the UI.
         */
//        viewModel.error.observe(viewLifecycleOwner) { error ->
//            if (error != null) {
//                loadingErrorTV.text = getString(R.string.loading_error, error.message)
//                loadingErrorTV.visibility = View.VISIBLE
//                Log.e(TAG, "Error fetching forecast: ${error.message}")
//            }
//        }

        /*
         * Set up an observer on the loading status of the API query.  Display the correct UI
         * elements based on the current loading status.
         */
//        viewModel.loading.observe(viewLifecycleOwner) { loading ->
//            if (loading) {
//                loadingIndicator.visibility = View.VISIBLE
//                loadingErrorTV.visibility = View.INVISIBLE
//                taskListRV.visibility = View.INVISIBLE
//            } else {
//                loadingIndicator.visibility = View.INVISIBLE
//            }
//        }

//        viewModel.debugHardcode()
    }

//    override fun onResume() {
//        super.onResume()
//
//        /*
//         * Here, we're reading the current preference values and triggering a data fetching
//         * operation in onResume().  This avoids the need to set up a preference change listener.
//         * It also means that a new API call could potentially be made every time the activity
//         * is resumed.  However, because of the basic caching that's implemented in the
//         * `FiveDayForecastRepository` class, an API call will actually only be made whenever
//         * the city or units setting changes (which is exactly what we want).
//         */
//        // TODO: Settings stuff goes here
//        // val sharedPrefs = PreferenceManager.getDefaultSharedPreferences(requireContext())
//        // val city = sharedPrefs.getString(getString(R.string.pref_city_key), "Corvallis,OR,US")
//        // val units = sharedPrefs.getString(getString(R.string.pref_units_key), null)
//        viewModel.loadTasks()
//    }

    /**
     * This method is passed into the RecyclerView adapter to handle clicks on individual items
     * in the list of forecast items.  When a forecast item is clicked, a new activity is launched
     * to view its details.
     */
    private fun onTaskItemClick(taskTemplate: TaskTemplate) {
        Log.d(TAG, "onTaskItemClick() called, task: $taskTemplate")
        val directions = OverviewFragmentDirections.navigateToTask(taskTemplate)
        findNavController().navigate(directions)
    }
}