package com.dsrcorporation.data.remote.network

import com.dsrcorporation.domain.models.make.MakeRes
import com.dsrcorporation.domain.models.models.ModelsRes
import com.dsrcorporation.domain.models.vehicle.VehicleRes
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("decodevinvalues/{vin}")
    suspend fun getVehicleByVIN(
        @Path("vin") vin: String,
        @Query("format") json: String
    ): Response<VehicleRes>

    // comes so much data from server, paging haven't been used 🫤
    @GET("getallmakes/")
    suspend fun getAllMakes(
        @Query("format") format: String
    ): Response<MakeRes>

    @GET("GetModelsForMakeId/{makeID}")
    suspend fun getModelsByMakeID(
        @Path("makeID") makeID: Int,
        @Query("format") format: String
    ): Response<ModelsRes>
}