package com.example.lifetracker.ui

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.lifetracker.data.TaskRecord
import com.example.lifetracker.data.TaskTemplate
import com.example.myapplication.R

class TaskDetailFragment : Fragment(R.layout.task_detail_fragment) {

    // get hooks for passed args
    private val args: TaskDetailFragmentArgs by navArgs()

    // Instantiate reference for views from layout
    private lateinit var recordListRV: RecyclerView

    // Instantiate viewModel and adapter
    private val viewModel: TaskViewModel by viewModels()
    private val recordAdapter = RecordAdapter(::onRecordItemClick)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        // Required magic
        super.onViewCreated(view, savedInstanceState)

        Log.d("TaskDetailFragment : onViewCreated","view created, ${args.taskName.name}")

        // Fetch and configure record recycler view
        recordListRV = view.findViewById(R.id.task_detail_record_RV)
        recordListRV.layoutManager = LinearLayoutManager(requireContext())
        recordListRV.setHasFixedSize(true)
        recordListRV.adapter = recordAdapter

        Log.d("TaskDetailFragment : onViewCreated","Count called from adapter, ${recordAdapter.itemCount}")


        viewModel.getAllRecordsForTask(args.taskName.name).observe(viewLifecycleOwner) {records ->
            Log.d("TaskDetailFragment : onViewCreated","observer found a thing: ${records.size}")
            recordAdapter.updateRecords(records)
        }

        view.findViewById<TextView>(R.id.task_detail_title).text = args.taskName.name
    }

    override fun onResume() {
        super.onResume()
        // This doesn't actually do anything!
    }

    // TODO: Make this
    //override fun onCreateOptionsMenu(menu: Menu, menuInflater: MenuInflater) {
    //    menuInflater.inflate(R.menu.activity_forecast_detail, menu)
    //}

    private fun onRecordItemClick(taskRecord: TaskRecord) {
        // Navigate to record detail screen
        Log.d("TaskDetailFragment", "Go to record detail view for ${taskRecord.stamp}")
    }
//
//    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        // TODO: Handle settings stuff
//        Log.d("TaskDetailFragment", "Settings functionality should go here...")
//        return super.onOptionsItemSelected(item)
//    }
}