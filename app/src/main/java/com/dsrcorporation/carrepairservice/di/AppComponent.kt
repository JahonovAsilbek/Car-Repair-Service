package com.dsrcorporation.carrepairservice.di

import com.dsrcorporation.carrepairservice.App
import com.dsrcorporation.carrepairservice.di.module.ApplicationModule
import com.dsrcorporation.carrepairservice.di.module.DataModule
import com.dsrcorporation.carrepairservice.di.module.RoomDatabaseModule
import com.dsrcorporation.carrepairservice.di.module.ViewModelModule
import com.dsrcorporation.carrepairservice.ui.add.AddOrderFragment
import com.dsrcorporation.carrepairservice.ui.all.OrdersFragment
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [ApplicationModule::class, DataModule::class, ViewModelModule::class, RoomDatabaseModule::class])
interface AppComponent {

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance app: App, applicationModule: ApplicationModule): AppComponent
    }

    fun inject(app: App)
    fun inject(allOrdersFragment: OrdersFragment)
    fun inject(addOrderFragment: AddOrderFragment)
}