package com.dsrcorporation.carrepairservice.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dsrcorporation.carrepairservice.utils.network.NetworkHelper
import com.dsrcorporation.carrepairservice.utils.network.Resource
import com.dsrcorporation.domain.interactor.VehicleInteractor
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

class AllOrdersViewModel @Inject constructor(
    private val vehicleInteractor: VehicleInteractor,
    private val networkHelper: NetworkHelper
) : ViewModel() {

    private val vehicle = MutableStateFlow<Resource>(Resource.Loading)

    fun getVehicle(vin: String): StateFlow<Resource> {
        viewModelScope.launch {
            if (networkHelper.isNetworkConnected()) {
                vehicleInteractor.getVehicle(vin).catch {
                    vehicle.emit(Resource.Error(it.message))
                }.collect {
                    if (it.isSuccess) {
                        val vehicleRes = it.getOrNull()
                        if (vehicleRes != null && vehicleRes.Result.isNotEmpty()) {
                            vehicle.emit(Resource.Success(it.getOrNull()!!.Result[0]))
                        }
                    } else {
                        vehicle.emit(Resource.Error(it.exceptionOrNull()))
                    }
                }
            } else {
                // get from database
            }
        }
        return vehicle
    }

}