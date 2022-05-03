package com.dsrcorporation.data.repository

import android.util.Log
import com.dsrcorporation.data.local.dao.VehicleDao
import com.dsrcorporation.data.remote.network.ApiService
import com.dsrcorporation.domain.models.make.Make
import com.dsrcorporation.domain.models.models.Model
import com.dsrcorporation.domain.models.order.Order
import com.dsrcorporation.domain.models.vehicle.Vehicle
import com.dsrcorporation.domain.network.NetworkHelper
import com.dsrcorporation.domain.repository.VehicleRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class VehicleRepositoryImp @Inject constructor(
    private val apiService: ApiService,
    private val vehicleDao: VehicleDao,
    private val networkhelper: NetworkHelper
) : VehicleRepository {

    override suspend fun getVehicle(vin: String): Flow<Result<Vehicle>> {
        return flow {
            val response = apiService.getVehicleByVIN(vin, "json")
            if (response.isSuccessful && response.body() != null) {
                Log.d("AAAA", "getVehicle:${response.body()} ")
                emit(Result.success(response.body()!!.Results[0]))
            }
        }
    }

    override suspend fun getAllMakes(): Flow<List<Make>> {
        return if (networkhelper.isNetworkConnected()) {
            flow {
                val response = apiService.getAllMakes("json")
                if (response.isSuccessful && response.body() != null) {
                    emit(response.body()!!.Results)
                    vehicleDao.insertMake(response.body()!!.Results)
                }
            }
        } else {
            vehicleDao.getAllMakes()
        }
    }

    override suspend fun getModelsByMakeID(makeID: Int): Flow<List<Model>> {
        return if (networkhelper.isNetworkConnected()) {
            flow {
                val response = apiService.getModelsByMakeID(makeID = makeID, format = "json")
                if (response.isSuccessful && response.body() != null) {
                    emit(response.body()!!.Results)
                    vehicleDao.insertModels(response.body()!!.Results)
                }
            }
        } else {
            vehicleDao.getModelsByMakeID(makeID = makeID)
        }
    }

    override suspend fun insertOrder(order: Order) {
        vehicleDao.insertOrder(order = order)
    }

    override suspend fun insertMake(makes: List<Make>) {
        vehicleDao.insertMake(makes = makes)
    }

    override suspend fun insertModels(models: List<Model>) {
        vehicleDao.insertModels(models = models)
    }

    override suspend fun changeOrderStatus(isClosed: Boolean, id: Int) {
        vehicleDao.changeOrderStatus(isClosed = isClosed, id = id)
    }

    override fun getOrderByID(id: Int): Flow<Order> {
        return vehicleDao.getOrderByID(id = id)
    }

    override fun getAllOrders(): Flow<List<Order>> {
        return vehicleDao.getAllOrders()
    }

    override fun getOrderByStatus(isClosed: Boolean): Flow<List<Order>> {
        return vehicleDao.getOrderByStatus(isClosed = isClosed)
    }
}