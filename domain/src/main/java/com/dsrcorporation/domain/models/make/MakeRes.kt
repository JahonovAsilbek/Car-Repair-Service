package com.dsrcorporation.domain.models.make

data class MakeRes(
    val Count: Int,
    val Message: String,
    val Results: List<Make>,
    val SearchCriteria: Any
)