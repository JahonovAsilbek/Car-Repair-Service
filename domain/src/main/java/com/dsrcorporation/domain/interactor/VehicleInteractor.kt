package com.dsrcorporation.domain.interactor

import com.dsrcorporation.domain.models.make.Make
import com.dsrcorporation.domain.models.models.Model
import com.dsrcorporation.domain.models.order.Order
import com.dsrcorporation.domain.models.vehicle.Vehicle
import com.dsrcorporation.domain.repository.VehicleRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class VehicleInteractor @Inject constructor(
    private val vehicleRepository: VehicleRepository,
) {

    suspend fun getVehicle(vin: String): Flow<Result<Vehicle>> {
        return vehicleRepository.getVehicle(vin)
    }

    suspend fun getAllMakes(): Flow<List<Make>> {
        return vehicleRepository.getAllMakes()
    }

    fun getAllOrders(isClosed: Boolean?): Flow<List<Order>> {
        return if (isClosed == null)
            vehicleRepository.getAllOrders()
        else
            vehicleRepository.getAllOrders(isClosed = isClosed)
    }

    suspend fun addOrder(order: Order) {
        vehicleRepository.insertOrder(order)
    }

    suspend fun getModelsByMakeID(makeID: Int): Flow<List<Model>> {
        return vehicleRepository.getModelsByMakeID(makeID)
    }

    suspend fun changeOrderStatus(isClosed: Boolean, id: Int) {
        return vehicleRepository.changeOrderStatus(isClosed = isClosed, id = id)
    }

    fun getOrderByStatus(isClosed: Boolean): Flow<List<Order>> {
        return vehicleRepository.getOrderByStatus(isClosed = isClosed)
    }

    private fun getOrderByModel(isClosed: Boolean?): Flow<List<Order>> {
        return if (isClosed != null)
            vehicleRepository.getOrderByModel(isClosed = isClosed)
        else
            vehicleRepository.getOrderByModel()
    }

    private fun getOrderByModelDesc(isClosed: Boolean?): Flow<List<Order>> {
        return if (isClosed != null)
            vehicleRepository.getOrderByModelDesc(isClosed = isClosed)
        else
            vehicleRepository.getOrderByModelDesc()
    }

    private fun getOrderByDate(isClosed: Boolean?): Flow<List<Order>> {
        return if (isClosed != null)
            vehicleRepository.getOrderByDate(isClosed = isClosed)
        else
            vehicleRepository.getOrderByDate()
    }

    private fun getOrderByDateDesc(isClosed: Boolean?): Flow<List<Order>> {
        return if (isClosed != null)
            vehicleRepository.getOrderByDateDesc(isClosed = isClosed)
        else
            vehicleRepository.getOrderByDateDesc()
    }

    fun getSortedOrder(isClosed: Boolean?, name: Boolean, nameDesc: Boolean, date: Boolean, dateDesc: Boolean): Flow<List<Order>> {
        return when {
            name -> getOrderByModel(isClosed = isClosed)
            nameDesc -> getOrderByModelDesc(isClosed = isClosed)
            date -> getOrderByDate(isClosed = isClosed)
            dateDesc -> getOrderByDateDesc(isClosed = isClosed)
            else -> {
                flow { }
            }
        }
    }

}