package com.dsrcorporation.carrepairservice.utils

import android.content.Context
import android.util.DisplayMetrics
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast

fun Int.dpToPx(displayMetrics: DisplayMetrics?): Int {
    return (this * displayMetrics!!.density).toInt()
}
fun EditText.text(): String = this.text.toString().trim()
fun TextView.text(): String = this.text.toString().trim()
fun Context.showToast(message: String?) = Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
fun View.visible() {
    this.visibility = View.VISIBLE
}

fun View.invisible() {
    this.visibility = View.INVISIBLE
}

fun View.gone() {
    this.visibility = View.GONE
}