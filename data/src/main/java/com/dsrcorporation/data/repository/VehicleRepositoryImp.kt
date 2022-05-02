package com.dsrcorporation.data.repository

import com.dsrcorporation.data.remote.network.ApiService
import com.dsrcorporation.domain.models.make.MakeRes
import com.dsrcorporation.domain.models.models.ModelsRes
import com.dsrcorporation.domain.models.vehicle.VehicleRes
import com.dsrcorporation.domain.repository.VehicleRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class VehicleRepositoryImp @Inject constructor(private val apiService: ApiService) : VehicleRepository {

    override suspend fun getVehicle(vin: String): Flow<Result<VehicleRes>> {
        return flow {
            val response = apiService.getVehicleByVIN(vin, "json")
            if (response.isSuccessful && response.body() != null) {
                flow { emit(Result.success(response.body()!!)) }
            }
        }
    }

    override suspend fun getAllMakes(): Flow<Result<MakeRes>> {
        return flow {
            val response = apiService.getAllMakes("json")
            if (response.isSuccessful && response.body() != null) {
                flow { emit(Result.success(response.body())) }
            }
        }
    }

    override suspend fun getModelsByMakeID(makeID: Int): Flow<Result<ModelsRes>> {
        return flow {
            val response = apiService.getModelsByMakeID(makeID, "json")
            if (response.isSuccessful && response.body() != null) {
                flow { emit(Result.success(response.body())) }
            }
        }
    }
}