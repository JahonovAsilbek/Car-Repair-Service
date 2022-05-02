package com.dsrcorporation.data.local.converters

import androidx.room.TypeConverter
import com.dsrcorporation.data.local.entity.Task
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class OrderConverter {
    @TypeConverter
    fun stringToObj(string: String): Task {
        val type = object : TypeToken<Task>() {}.type
        return Gson().fromJson(string, type)
    }

    @TypeConverter
    fun objToString(list: Task): String {
        val type = object : TypeToken<Task>() {}.type
        return Gson().toJson(list, type)
    }

}