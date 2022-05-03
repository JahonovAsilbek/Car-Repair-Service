package com.dsrcorporation.domain.repository

import com.dsrcorporation.domain.models.make.Make
import com.dsrcorporation.domain.models.models.Model
import com.dsrcorporation.domain.models.order.Order
import com.dsrcorporation.domain.models.vehicle.Vehicle
import kotlinx.coroutines.flow.Flow

interface VehicleRepository {
    suspend fun getVehicle(vin: String): Flow<Result<Vehicle>>
    suspend fun getAllMakes(): Flow<List<Make>>
    suspend fun getModelsByMakeID(makeID: Int): Flow<List<Model>>
    suspend fun insertOrder(order: Order)
    suspend fun insertMake(makes: List<Make>)
    suspend fun insertModels(models: List<Model>)
    suspend fun changeOrderStatus(isOpened: Boolean, id: Int)
    fun getOrderByID(id: Int): Flow<Order>
    fun getAllOrders(): Flow<List<Order>>
}