package com.dsrcorporation.carrepairservice

import android.app.Application
import com.dsrcorporation.carrepairservice.di.AppComponent
import com.dsrcorporation.carrepairservice.di.DaggerAppComponent
import com.dsrcorporation.carrepairservice.di.module.ApplicationModule
import com.dsrcorporation.domain.network.NetworkHelper
import javax.inject.Inject

class App @Inject constructor() : Application() {

    companion object {
        lateinit var appComponent: AppComponent
    }

    override fun onCreate() {
        super.onCreate()
        NetworkHelper(this)

        appComponent = DaggerAppComponent.factory().create(this, ApplicationModule(this))

        appComponent.inject(this)
    }
}
