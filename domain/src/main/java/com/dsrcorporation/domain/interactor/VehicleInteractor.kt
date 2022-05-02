package com.dsrcorporation.domain.interactor

import com.dsrcorporation.domain.models.vehicle.VehicleRes
import com.dsrcorporation.domain.repository.VehicleRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class VehicleInteractor @Inject constructor(private val vehicleRepository: VehicleRepository) {

    suspend fun getVehicle(vin: String): Flow<Result<VehicleRes>> {
        return vehicleRepository.getVehicle(vin)
    }

}