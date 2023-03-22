package com.example.lifetracker.ui

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.example.myapplication.R
//import org.jetbrains.annotations.
import jetbrains.letsPlot.export.ggsave
import jetbrains.letsPlot.geom.geomPoint
import jetbrains.letsPlot.letsPlot

//import

fun genericGraphFunc() {
    Log.d("TaskSummaryFragment: genericGraphFunc", "In the generic graphing function!")
    val xs = listOf(0,  0.5, 1, 2)
    val ys = listOf(0, 0.25, 1, 4)
    val data = mapOf<String, Any>("x" to xs, "y" to ys)

    val fig = letsPlot(data) + geomPoint(
        color = "dark-blue",
        size = 4.0
    ) { x = "x"; y = "y" }

    ggsave(fig, "plot.png")
}

class TaskSummaryFragment : Fragment(R.layout.task_summary_fragment) {
    // Instantiate viewModel and adapter
    private val viewModel: TaskViewModel by viewModels()

    // get hooks for passed args
    private val args: TaskSummaryFragmentArgs by navArgs()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d("TaskSummaryFragment: onViewCreaated", "In this place...")

        genericGraphFunc()
    }
}