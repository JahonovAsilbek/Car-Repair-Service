package com.dsrcorporation.carrepairservice.di.module

import android.content.Context
import androidx.room.Room
import com.dsrcorporation.data.local.AppDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RoomDatabaseModule {

    private lateinit var appDatabase: AppDatabase

    @Provides
    @Singleton
    fun provideRoomDatabase(context: Context): AppDatabase {
        appDatabase = Room.databaseBuilder(context, AppDatabase::class.java, "dsr.db")
            .fallbackToDestructiveMigration()
            .allowMainThreadQueries()
            .build()
        return appDatabase
    }

    @Provides
    @Singleton
    fun provideOrderDao(appDatabase: AppDatabase) = appDatabase.vehicleDao()

}