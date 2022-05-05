package com.dsrcorporation.carrepairservice.ui.all

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
    fun getAllorders(isClosed: Boolean?): StateFlow<Resource> {
        viewModelScope.launch {
            vehicleInteractor.getAllOrders(isClosed).catch {
                ordersRes.emit(Resource.Error("Error"))
            }.collect {
                ordersRes.emit(Resource.Success(it))
            }
        }
        return ordersRes
    }

    fun changeOrderStatus(isClosed: Boolean, id: Int) {
        viewModelScope.launch {
            vehicleInteractor.changeOrderStatus(isClosed, id)
        }
    }

    private val orderByStatus = MutableStateFlow<Resource>(Resource.Loading)
    fun getOrderByStatus(isClosed: Boolean): StateFlow<Resource> {
        viewModelScope.launch {
            vehicleInteractor.getOrderByStatus(isClosed = isClosed).catch {
                orderByStatus.emit(Resource.Error("Error"))
            }.collect {
                orderByStatus.emit(Resource.Success(it))
            }
        }
        return orderByStatus
    }

    private val sortedOrder = MutableStateFlow<Resource>(Resource.Loading)
    fun getSortedOrder(isClosed: Boolean?, name: Boolean, nameDesc: Boolean, date: Boolean, dateDesc: Boolean): StateFlow<Resource> {
        viewModelScope.launch {
            vehicleInteractor.getSortedOrder(isClosed, name, nameDesc, date, dateDesc).catch {
                sortedOrder.emit(Resource.Error("Error"))
            }.collect {
                sortedOrder.emit(Resource.Success(it))
            }
        }
        return sortedOrder
    }
}