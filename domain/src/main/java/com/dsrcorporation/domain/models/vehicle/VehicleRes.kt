package com.dsrcorporation.domain.models.vehicle

data class VehicleRes(
    val Count: Int,
    val Message: String,
    val Result: List<Vehicle>,
    val SearchCriteria: String
)