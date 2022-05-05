package com.dsrcorporation.carrepairservice.utils.storage

import android.content.Context
import android.content.SharedPreferences

object

MyLocalStorage {
    private const val NAME = "dsr"
    private const val MODE = Context.MODE_PRIVATE
    private lateinit var sharedPreferences: SharedPreferences

    fun init(context: Context) {
        sharedPreferences = context.getSharedPreferences(NAME, MODE)
    }

    private inline fun SharedPreferences.edit(operation: (SharedPreferences.Editor) -> Unit) {
        val editor = edit()
        operation(editor)
        editor.apply()
    }

    var language: String?
        get() = sharedPreferences.getString("language", "")
        set(value) = sharedPreferences.edit {
            if (value != null) {
                it.putString("language", value)
            }
        }
}