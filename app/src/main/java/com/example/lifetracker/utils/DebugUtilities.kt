package com.example.lifetracker.utils

import android.content.Context
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext


// https://www.digitalocean.com/community/tutorials/android-spinner-using-kotlin
public fun showToast(context: Context, message: String, duration: Int = Toast.LENGTH_LONG) {
    Toast.makeText(context, message, duration).show()
}