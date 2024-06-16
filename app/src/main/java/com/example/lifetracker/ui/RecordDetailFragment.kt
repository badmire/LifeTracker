package com.example.lifetracker.ui

import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.lifetracker.data.TaskRecord
import com.example.lifetracker.utils.milisecondToString
import com.example.myapplication.R
import com.google.android.material.snackbar.Snackbar

class RecordDetailFragment : Fragment(R.layout.activity_record_detail) {
    // Instantiate viewModel and adapter
    private val viewModel: TaskViewModel by viewModels()

    // get hooks for passed args
    private val args: RecordDetailFragmentArgs by navArgs()

    // Get hooks for views
    private lateinit var titleTV : TextView
    private lateinit var noteEV : EditText
    private lateinit var submitButton : Button
    private lateinit var deleteButton : Button

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        // Required Magic
        super.onViewCreated(view, savedInstanceState)

        titleTV = view.findViewById(R.id.record_detail_title_tv)
        noteEV = view.findViewById(R.id.record_detail_edit_note)
        submitButton = view.findViewById(R.id.record_detail_submit_button)
        deleteButton = view.findViewById(R.id.record_detail_delete_button)

        // Set stamp as title
        titleTV.text = milisecondToString(args.taskRecord.stamp)

        // Check if a note exists
        if (args.taskRecord.note != null) {
            noteEV.setText(args.taskRecord.note)
        } else {
            noteEV.setHint("Type note here...")
        }

        submitButton.setOnClickListener {
            viewModel.addTaskRecord(
                TaskRecord(
                    args.taskRecord.stamp,
                    args.taskRecord.template,
                    args.taskRecord.value,
                    noteEV.text.toString()
                )
            )
            // Hide keyboard
            // https://stackoverflow.com/questions/1109022/how-to-close-hide-the-android-soft-keyboard-programmatically
            (context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager)
                .hideSoftInputFromWindow(view.windowToken,0)

            // Give submission feedback
            Snackbar.make(view,getString(R.string.record_update_feedback),Snackbar.LENGTH_LONG).show()
        }

        deleteButton.setOnClickListener {
            viewModel.deleteTaskRecord(args.taskRecord)
            val directions = RecordDetailFragmentDirections.navigateToTaskDetails(args.taskTemplate)
            findNavController().navigate(directions)
        }
    }
}