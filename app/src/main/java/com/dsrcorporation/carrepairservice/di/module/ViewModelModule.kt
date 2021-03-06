package com.dsrcorporation.carrepairservice.di.module

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dsrcorporation.carrepairservice.ui.add.AddOrderViewModel
import com.dsrcorporation.carrepairservice.ui.all.AllOrdersViewModel
import com.dsrcorporation.carrepairservice.ui.single.OrderInfoViewModel
import com.dsrcorporation.carrepairservice.vm.ViewModelFactory
import com.dsrcorporation.carrepairservice.vm.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface ViewModelModule {
    @Binds
    fun bindViewModelFactory(viewModelFactory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(AllOrdersViewModel::class)
    fun bindAllOrdersViewModel(allOrdersViewModel: AllOrdersViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(AddOrderViewModel::class)
    fun bindAddOrderViewModel(addOrderViewModel: AddOrderViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(OrderInfoViewModel::class)
    fun bindOrderInfoViewModel(orderInfoViewModel: OrderInfoViewModel): ViewModel
}