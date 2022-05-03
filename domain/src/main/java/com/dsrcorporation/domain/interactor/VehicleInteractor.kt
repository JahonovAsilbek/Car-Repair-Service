package com.dsrcorporation.domain.interactor

import com.dsrcorporation.domain.models.make.Make
import com.dsrcorporation.domain.models.models.Model
import com.dsrcorporation.domain.models.order.Order
import com.dsrcorporation.domain.models.vehicle.Vehicle
import com.dsrcorporation.domain.repository.VehicleRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.coroutines.CoroutineContext

@Singleton
class VehicleInteractor @Inject constructor(
    private val vehicleRepository: VehicleRepository,
) : CoroutineScope {

    suspend fun getVehicle(vin: String): Flow<Result<Vehicle>> {
        return vehicleRepository.getVehicle(vin)
    }

    suspend fun getAllMakes(): Flow<List<Make>> {
        return vehicleRepository.getAllMakes()
    }

    fun getAllOrders(): Flow<List<Order>> {
        return vehicleRepository.getAllOrders()
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

    override val coroutineContext: CoroutineContext
        get() = Job()


}