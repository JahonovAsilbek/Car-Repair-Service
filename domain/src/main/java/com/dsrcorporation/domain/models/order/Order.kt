package com.dsrcorporation.domain.models.order

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "repair_order")
class Order : Serializable {
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null
    var vin:String
    var model: String
    var make: String
    var serialNumber: String
    var date: Long = 0
    var tasks: List<Task>
    var isClosed: Boolean

    constructor(vin: String, model: String, make: String, serialNumber: String, date: Long, tasks: List<Task>, isClosed: Boolean) {
        this.vin = vin
        this.model = model
        this.make = make
        this.serialNumber = serialNumber
        this.date = date
        this.tasks = tasks
        this.isClosed = isClosed
    }
}
