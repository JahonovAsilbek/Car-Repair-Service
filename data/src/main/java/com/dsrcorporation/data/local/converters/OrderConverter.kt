package com.dsrcorporation.data.local.converters

import androidx.room.TypeConverter
import com.dsrcorporation.domain.models.order.Task
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class OrderConverter {
    @TypeConverter
    fun listToObj(string: String): List<Task> {
        val type = object : TypeToken<List<Task>>() {}.type
        return Gson().fromJson(string, type)
    }

    @TypeConverter
    fun objToList(list: List<Task>): String {
        val type = object : TypeToken<List<Task>>() {}.type
        return Gson().toJson(list, type)
    }
}