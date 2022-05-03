package com.dsrcorporation.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.dsrcorporation.data.local.converters.OrderConverter
import com.dsrcorporation.data.local.dao.VehicleDao
import com.dsrcorporation.domain.models.make.Make
import com.dsrcorporation.domain.models.models.Model
import com.dsrcorporation.domain.models.order.Order

@Database(entities = [Make::class, Model::class, Order::class], version = 1, exportSchema = false)
@TypeConverters(OrderConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun vehicleDao(): VehicleDao
}