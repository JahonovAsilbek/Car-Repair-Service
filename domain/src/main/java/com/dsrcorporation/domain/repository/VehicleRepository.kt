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
    suspend fun changeOrderStatus(isClosed: Boolean, id: Int)
    fun getOrderByID(id: Int): Flow<Order>
    fun getAllOrders(): Flow<List<Order>>
    fun getAllOrders(isClosed: Boolean): Flow<List<Order>>
    fun getOrderByStatus(isClosed: Boolean): Flow<List<Order>>
    fun getOrderByModel(isClosed: Boolean): Flow<List<Order>>
    fun getOrderByModelDesc(isClosed: Boolean): Flow<List<Order>>
    fun getOrderByDate(isClosed: Boolean): Flow<List<Order>>
    fun getOrderByDateDesc(isClosed: Boolean): Flow<List<Order>>
    fun getOrderByModel(): Flow<List<Order>>
    fun getOrderByModelDesc(): Flow<List<Order>>
    fun getOrderByDate(): Flow<List<Order>>
    fun getOrderByDateDesc(): Flow<List<Order>>
}