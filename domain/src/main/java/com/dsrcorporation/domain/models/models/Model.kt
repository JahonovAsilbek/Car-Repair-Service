package com.dsrcorporation.domain.models.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Model(
    @ColumnInfo(name = "makeID")
    val Make_ID: Int,
    val Make_Name: String,
    @PrimaryKey
    val Model_ID: Int,
    val Model_Name: String
)