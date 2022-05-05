package com.dsrcorporation.carrepairservice

import android.app.Application
import com.dsrcorporation.carrepairservice.di.AppComponent
import com.dsrcorporation.carrepairservice.di.DaggerAppComponent
import com.dsrcorporation.carrepairservice.di.module.ApplicationModule
import com.dsrcorporation.carrepairservice.utils.localization.LocaleHelper
import com.dsrcorporation.carrepairservice.utils.storage.MyLocalStorage
import javax.inject.Inject

class App @Inject constructor() : Application() {

    companion object {
        lateinit var appComponent: AppComponent
    }

    override fun onCreate() {
        super.onCreate()
        MyLocalStorage.init(this)
        LocaleHelper().onAttach(this, MyLocalStorage.language!!)

        appComponent = DaggerAppComponent.factory().create(this, ApplicationModule(this))

        appComponent.inject(this)
    }
}
