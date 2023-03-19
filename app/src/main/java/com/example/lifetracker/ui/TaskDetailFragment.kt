package com.example.lifetracker.ui

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.example.lifetracker.data.TaskRecord
import com.example.lifetracker.data.TaskTemplate
import com.example.myapplication.R

class TaskDetailFragment : Fragment(R.layout.task_detail_fragment) {
    private val args: TaskDetailFragmentArgs by navArgs()
    private var taskTemplate: TaskTemplate? = null
    private var taskRecord: TaskRecord? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setHasOptionsMenu(true)

        taskTemplate = args.taskTemplate
        taskRecord = args.taskRecord

        // TODO: Settings stuff?

        view.findViewById<TextView>(R.id.tv_name).text = taskTemplate!!.name
        view.findViewById<TextView>(R.id.tv_icon).text = "Not done!"  // TODO: Fix this, should be based on whether or not total record value is >= db goal
        // TODO: This is just so very wrong...
        view.findViewById<TextView>(R.id.tv_time_stamp).text = getString(
            R.string.task_time,
            openWeatherEpochToDate(forecastPeriod!!.epoch, forecastCity!!.tzOffsetSec)
        )
    }
}