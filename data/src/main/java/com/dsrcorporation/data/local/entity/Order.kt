package com.dsrcorporation.data.local.entity

import androidx.room.PrimaryKey

data class Order(
    @PrimaryKey val id: Int,
    val model: String,
    val make: String,
    val serialNumber: String,
    val date: String,
    val tasks: List<Task>,
    val isOpened: Boolean
)
