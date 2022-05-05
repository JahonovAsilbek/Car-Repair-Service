package com.dsrcorporation.carrepairservice.ui.single

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dsrcorporation.domain.interactor.VehicleInteractor
import kotlinx.coroutines.launch
import javax.inject.Inject

class OrderInfoViewModel @Inject constructor(
    private val vehicleInteractor: VehicleInteractor
) : ViewModel() {

    fun changeOrderStatus(isClosed: Boolean, id: Int) {
        viewModelScope.launch {
            vehicleInteractor.changeOrderStatus(isClosed, id)
        }
    }

}