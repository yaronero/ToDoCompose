package com.example.todocompose.util

import android.content.Context
import android.widget.Toast

fun displayToast(context: Context, text: String) {
    Toast.makeText(
        context,
        text,
        Toast.LENGTH_SHORT
    ).show()
}