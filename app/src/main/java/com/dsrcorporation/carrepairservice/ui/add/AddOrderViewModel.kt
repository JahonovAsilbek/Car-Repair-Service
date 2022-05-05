package com.dsrcorporation.carrepairservice.ui.add

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dsrcorporation.carrepairservice.utils.network.Resource
import com.dsrcorporation.domain.interactor.VehicleInteractor
import com.dsrcorporation.domain.models.order.Order
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

class AddOrderViewModel @Inject constructor(
    private val vehicleInteractor: VehicleInteractor
) : ViewModel() {

    fun addOrder(order: Order) {
        viewModelScope.launch {
            vehicleInteractor.addOrder(order)
        }
    }

    private val vinRes = MutableSharedFlow<Resource>()
    fun getVehicle(vin: String): SharedFlow<Resource> {
        viewModelScope.launch {
            vinRes.emit(Resource.Loading)
            vehicleInteractor.getVehicle(vin).catch {
                vinRes.emit(Resource.Error(it.message))
                Log.d("AAAA", "getVehicle:${it.message} ")
            }.collect {
                val vehicle = it.getOrNull()
                if (vehicle != null) {
                    if (vehicle.Make.isNotEmpty() && vehicle.Model.isNotEmpty())
                        vinRes.emit(Resource.Success(vehicle))
                    else
                        vinRes.emit(Resource.Error(it.exceptionOrNull()))
                } else {
                    vinRes.emit(Resource.Error(it.exceptionOrNull()))
                }
            }
        }
        return vinRes
    }

    private var allMakeRes = MutableStateFlow<Resource>(Resource.Loading)
    fun getAllMakes(): StateFlow<Resource> {
        viewModelScope.launch {
            vehicleInteractor.getAllMakes().catch {
                allMakeRes.emit(Resource.Error(it.message))
            }.collect {
                allMakeRes.emit(Resource.Success(it))
            }
        }
        return allMakeRes
    }

    private var modelsByMakeID = MutableStateFlow<Resource>(Resource.Loading)
    fun getModelsByMakeID(makeID: Int): StateFlow<Resource> {
        viewModelScope.launch {
            modelsByMakeID.emit(Resource.Loading)
            vehicleInteractor.getModelsByMakeID(makeID).collect {
                modelsByMakeID.emit(Resource.Success(it))
            }
        }
        return modelsByMakeID
    }
}