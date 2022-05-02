package com.dsrcorporation.domain.repository

import com.dsrcorporation.domain.models.make.MakeRes
import com.dsrcorporation.domain.models.models.ModelsRes
import com.dsrcorporation.domain.models.vehicle.VehicleRes
import kotlinx.coroutines.flow.Flow

interface VehicleRepository {
    suspend fun getVehicle(vin: String): Flow<Result<VehicleRes>>
    suspend fun getAllMakes(): Flow<Result<MakeRes>>
    suspend fun getModelsByMakeID(makeID: Int): Flow<Result<ModelsRes>>
}