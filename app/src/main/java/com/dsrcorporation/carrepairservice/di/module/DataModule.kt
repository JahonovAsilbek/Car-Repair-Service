package com.dsrcorporation.carrepairservice.di.module

import com.dsrcorporation.data.remote.network.ApiService
import com.dsrcorporation.data.repository.VehicleRepositoryImp
import com.dsrcorporation.domain.repository.VehicleRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import javax.inject.Singleton

@Module(includes = [DataModule.BindModule::class])
class DataModule {
    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): ApiService = retrofit.create(ApiService::class.java)

    @Module
    abstract class BindModule {
        @Binds
        abstract fun bindApiRepository(vehicleRepositoryImp: VehicleRepositoryImp): VehicleRepository
    }
}