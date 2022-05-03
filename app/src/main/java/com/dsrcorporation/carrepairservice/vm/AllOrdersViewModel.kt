package com.dsrcorporation.carrepairservice.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dsrcorporation.carrepairservice.utils.network.Resource
import com.dsrcorporation.domain.interactor.VehicleInteractor
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

class AllOrdersViewModel @Inject constructor(
    private val vehicleInteractor: VehicleInteractor
) : ViewModel() {

    private val ordersRes = MutableStateFlow<Resource>(Resource.Loading)
    fun getAllorders(): StateFlow<Resource> {
        viewModelScope.launch {
            vehicleInteractor.getAllOrders().catch {
                ordersRes.emit(Resource.Error("Error"))
            }.collect {
                ordersRes.emit(Resource.Success(it))
            }
        }
        return ordersRes
    }
}