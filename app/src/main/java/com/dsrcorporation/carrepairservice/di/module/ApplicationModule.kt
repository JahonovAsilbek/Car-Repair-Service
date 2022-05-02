package com.dsrcorporation.carrepairservice.di.module

import android.content.Context
import androidx.room.PrimaryKey
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.dsrcorporation.carrepairservice.BuildConfig
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Singleton

@Module
class ApplicationModule @Inject constructor(private val context: Context) {

    @Provides
    @Singleton
    fun provideContext(): Context = context

    @Provides
    @Singleton
    fun provideBaseUrl(): String = BuildConfig.API_BASE_URL

    @Provides
    @Singleton
    fun provideGsonConvertor(): GsonConverterFactory = GsonConverterFactory.create()

    @Provides
    @Singleton
    fun provideChuckerInterceptor(context: Context): ChuckerInterceptor {
        return ChuckerInterceptor.Builder(context)
            .maxContentLength(500_000L)
            .alwaysReadResponseBody(true)
            .build()
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(chuckerInterceptor: ChuckerInterceptor): OkHttpClient {
        val okHttpClient = OkHttpClient.Builder()
        okHttpClient.addInterceptor(chuckerInterceptor)
        okHttpClient.connectTimeout(5, TimeUnit.MINUTES)
        okHttpClient.writeTimeout(5, TimeUnit.MINUTES)
        okHttpClient.readTimeout(5, TimeUnit.MINUTES)
        return okHttpClient.build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(
        baseUrl: String,
        converterFactory: GsonConverterFactory,
        okHttpClient: OkHttpClient
    ): Retrofit {
        return Retrofit
            .Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(converterFactory)
            .client(okHttpClient)
            .build()
    }
}