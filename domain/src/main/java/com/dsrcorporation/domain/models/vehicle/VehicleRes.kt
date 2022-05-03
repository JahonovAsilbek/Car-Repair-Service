package com.dsrcorporation.domain.models.vehicle

data class VehicleRes(
    val Count: Int,
    val Message: String,
    val Results: List<Vehicle>,
    val SearchCriteria: String
)