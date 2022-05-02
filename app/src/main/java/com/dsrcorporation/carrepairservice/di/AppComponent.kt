package com.dsrcorporation.carrepairservice.di

import com.dsrcorporation.carrepairservice.App
import com.dsrcorporation.carrepairservice.di.module.ApplicationModule
import com.dsrcorporation.carrepairservice.di.module.DataModule
import com.dsrcorporation.carrepairservice.di.module.ViewModelModule
import com.dsrcorporation.carrepairservice.ui.all.AllOrdersFragment
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [ApplicationModule::class, DataModule::class, ViewModelModule::class])
interface AppComponent {

    @Component.Factory
    interface Factory {
       fun create(@BindsInstance app: App, applicationModule: ApplicationModule): AppComponent
    }

    fun inject(app: App)
    fun inject(allOrdersFragment: AllOrdersFragment)
}