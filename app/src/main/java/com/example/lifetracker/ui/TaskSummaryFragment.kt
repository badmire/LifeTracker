package com.example.lifetracker.ui

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.example.myapplication.R
import com.jjoe64.graphview.GraphView
import com.jjoe64.graphview.series.DataPoint
import com.jjoe64.graphview.series.LineGraphSeries

class TaskSummaryFragment : Fragment(R.layout.task_summary_fragment) {
    // Instantiate viewModel and adapter
    private val viewModel: TaskViewModel by viewModels()

    // get hooks for passed args
    private val args: TaskSummaryFragmentArgs by navArgs()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d("TaskSummaryFragment: onViewCreaated", "In this place...")

        // TODO: Graphing code from someone else's repo, we gotto do this better somehow
        //SurfaceView(requireContext())
        val graph = view.findViewById<GraphView>(R.id.graph)
        graph.setVisibility(View.VISIBLE)

        try {
            val series = LineGraphSeries(
                arrayOf( // on below line we are adding
                    // each point on our x and y axis.
                    DataPoint(0.0, 5.0),
                    DataPoint(1.0, 3.0),
                    DataPoint(2.0, 4.0),
                    DataPoint(3.0, 9.0),
                    DataPoint(4.0, 6.0),
                    DataPoint(5.0, 3.0),
                    DataPoint(6.0, 6.0),
                    DataPoint(7.0, 1.0),
                    DataPoint(8.0, 2.0),
                    DataPoint(9.0, 5.0),
                    DataPoint(10.0, 7.0),
                    DataPoint(11.0, 2.0),
                    DataPoint(12.0, 9.0)
                )
            )
            graph.addSeries(series)
        } catch (e: IllegalArgumentException) {
            Log.d(tag, "Error: $e")
        }
    }
}