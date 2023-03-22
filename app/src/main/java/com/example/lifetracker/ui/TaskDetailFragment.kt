package com.example.lifetracker.ui

import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat.getColor
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.lifetracker.data.TaskRecord
import com.example.lifetracker.data.TaskTemplate
import com.example.lifetracker.utils.milisecondToString
import com.example.myapplication.R

class TaskDetailFragment : Fragment(R.layout.task_detail_fragment) {

    // get hooks for passed args
    private val args: TaskDetailFragmentArgs by navArgs()

    // Instantiate reference for views from layout
    private lateinit var recordListRV: RecyclerView

    // Instantiate I/O container container
    private lateinit var IOcontainer: LinearLayout

    // Instantiate Status container container
    private lateinit var statusContainer: LinearLayout

    // Instantiate viewModel and adapter
    private val viewModel: TaskViewModel by viewModels()
    private val recordAdapter = RecordAdapter(::onRecordItemClick)

    fun updateGoals(template: TaskTemplate) {
        // Set Progress and I/O
        when (template.type) {
            1 -> { // Count to goal
                view?.findViewById<TextView>(R.id.task_detail_count_up_status)?.text =
                    recordAdapter.taskRecords.size.toString() + " / " + args.taskTemplate.goal.toString()
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        // Required magic
        super.onViewCreated(view, savedInstanceState)

        Log.d("TaskDetailFragment : onViewCreated","view created, title: ${args.taskTemplate.name}")

        // Call on fragment to put buttons in action bar
        setHasOptionsMenu(true)

        // Fetch and configure record recycler view
        recordListRV = view.findViewById(R.id.task_detail_record_RV)
        recordListRV.layoutManager = LinearLayoutManager(requireContext())
        recordListRV.setHasFixedSize(true)
        recordListRV.adapter = recordAdapter

        Log.d("TaskDetailFragment : onViewCreated","Count called from adapter, ${recordAdapter.itemCount}")

        // Set listener for records data
        viewModel.getAllRecordsForTask(args.taskTemplate.name).observe(viewLifecycleOwner) {records ->
            Log.d("TaskDetailFragment : onViewCreated","observer found this many things: ${records.size}")
            recordAdapter.updateRecords(records)
            updateGoals(args.taskTemplate)
            // Set last stamp
            if (recordAdapter.taskRecords.size > 0) {
                view.findViewById<TextView>(R.id.task_detail_previous_entry).text = "Last Entry @ " + milisecondToString(recordAdapter.taskRecords[0].stamp)
                if (recordAdapter.taskRecords.size >= args.taskTemplate!!.goal!!) {
                    view.findViewById<TextView>(R.id.task_detail_count_up_status).setTextColor(
                        getColor(requireContext(),R.color.figma_teal))
                } else {
                    view.findViewById<TextView>(R.id.task_detail_count_up_status).setTextColor(
                        getColor(requireContext(),R.color.figma_red))
                }
            }
            Log.d("TaskDetailFragment : onViewCreated","Count called from adapter post update, ${recordAdapter.itemCount}")
        }

        // Set Title
        view.findViewById<TextView>(R.id.task_detail_title).text = args.taskTemplate.name

        // Grab hooks to I/O
        IOcontainer = view.findViewById(R.id.task_detail_IO)

        statusContainer = view.findViewById(R.id.task_detail_progress_container)

        // Set Progress and I/O
        when (args.taskTemplate.type) {
            1 -> { // Count to goal
                val child = LayoutInflater.from(requireContext())
                    .inflate(R.layout.task_detail_count_up_status,null)

                // Finally, add inflated layout to container
                IOcontainer.addView(child)
            }
        }

        // Add record button functionality
        val buttonContainer = view.findViewById<Button>(R.id.task_detail_add_record_button)
        buttonContainer.setOnClickListener {
            Log.d("TaskDetailFragment : onAddRecordButtonClick", "Here we are!")
            // TODO: value and note should probably not be null...
            val taskRecord = TaskRecord(System.currentTimeMillis(), args.taskTemplate.name, 1, null)
            viewModel.addTaskRecord(taskRecord)
        }
    }

    private fun onRecordItemClick(taskRecord: TaskRecord) {
        // Navigate to record detail screen
        Log.d("TaskDetailFragment", "Go to record detail view for ${taskRecord.stamp}")
        val directions = TaskDetailFragmentDirections.navigateToRecordDetail(taskRecord)
        findNavController().navigate(directions)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.task_detail_action_bar,menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        Log.d("TaskDetailFragment", "${item.title} button pushed")
        return when (item.itemId) {
            R.id.task_detail_menu_summary -> {
                Log.d("TaskDetailFragment", "Inside ${item.title} block")
                val directions = TaskDetailFragmentDirections.navigateToTaskSummary(args.taskTemplate)
                findNavController().navigate(directions)
                true
            }
            R.id.task_detail_task_settings -> {
                Log.d("TaskDetailFragment", "Inside ${item.title} block")
                val directions = TaskDetailFragmentDirections.navigateToTaskSettings(args.taskTemplate)
                findNavController().navigate(directions)
                true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }
}