package com.example.lifetracker.ui

import android.os.Bundle
import android.text.format.DateUtils
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.example.lifetracker.data.TaskRecord
import com.example.myapplication.R
import com.jjoe64.graphview.GraphView
import com.jjoe64.graphview.series.DataPoint
import com.jjoe64.graphview.series.LineGraphSeries

class TaskSummaryFragment : Fragment(R.layout.task_summary_fragment) {
    // Instantiate viewModel and adapter
    private val viewModel: TaskViewModel by viewModels()

    // get hooks for passed args
    private val args: TaskSummaryFragmentArgs by navArgs()

    // Get hook for records
    private lateinit var taskRecords: List<TaskRecord>
    private lateinit var graphView : GraphView
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d("TaskSummaryFragment: onViewCreaated", "In this place...")

        viewModel
            .getAllRecordsForTask(args.taskTemplate.name)
            .observe(viewLifecycleOwner) {
                this.taskRecords = it
                populateGraph(this.taskRecords)
            }

        // TODO: Graphing code from someone else's repo, we could do this better
        //SurfaceView(requireContext())
        graphView = view.findViewById<GraphView>(R.id.graph)
        graphView.setVisibility(View.INVISIBLE)
    }
    fun populateGraph(target: List<TaskRecord>) {
        val graph = graphView
        try {
            // Initialize an empty array of DataPoints
            val array = arrayOf(DataPoint(0.0, 0.0),
                DataPoint(0.0, 0.0),
                DataPoint(0.0, 0.0),
                DataPoint(0.0, 0.0),
                DataPoint(0.0, 0.0),
                DataPoint(0.0, 0.0),
                DataPoint(0.0, 0.0),
                DataPoint(0.0, 0.0))

            // Fill that array with stuff from the taskRecords
            for (arrayIndex in array.indices) {
                if (this.taskRecords.size >= arrayIndex) {
                    array[arrayIndex] =
                        DataPoint(arrayIndex.toDouble(), taskRecords[arrayIndex].value.toDouble())
                }
            }

            // Display
            val series = LineGraphSeries(array)

//            val series = LineGraphSeries(
//                arrayOf( // on below line we are adding
//                    // each point on our x and y axis.
//                    DataPoint(0.0, eightDaysAgoValue),
//                    DataPoint(1.0, sevenDaysAgoValue),
//                    DataPoint(2.0, sixDaysAgoValue),
//                    DataPoint(3.0, fiveDaysAgoValue),
//                    DataPoint(4.0, fourDaysAgoValue),
//                    DataPoint(5.0, threeDaysAgoValue),
//                    DataPoint(6.0, twoDaysAgoValue),
//                    DataPoint(7.0, dayAgoValue),
//                    DataPoint(8.0, todayValue)
//                )
//            )
            graph.addSeries(series)
        } catch (e: IllegalArgumentException) {
            Log.d(tag, "Error: $e")
        }
        graph.setVisibility(View.VISIBLE)
    }
}