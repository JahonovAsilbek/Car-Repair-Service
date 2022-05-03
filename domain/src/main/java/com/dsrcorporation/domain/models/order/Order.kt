package com.dsrcorporation.domain.models.order

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "repair_order")
class Order {
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null
    var model: String
    var make: String
    var serialNumber: String
    var date: Long = 0
    var tasks: List<Task>
    var isClosed: Boolean

    constructor(model: String, make: String, serialNumber: String, date: Long, tasks: List<Task>, isClosed: Boolean) {
        this.model = model
        this.make = make
        this.serialNumber = serialNumber
        this.date = date
        this.tasks = tasks
        this.isClosed = isClosed
    }
}
