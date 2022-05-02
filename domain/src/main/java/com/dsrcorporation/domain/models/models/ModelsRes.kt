package com.dsrcorporation.domain.models.models

data class ModelsRes(
    val Count: Int,
    val Message: String,
    val Results: List<Model>,
    val SearchCriteria: String
)