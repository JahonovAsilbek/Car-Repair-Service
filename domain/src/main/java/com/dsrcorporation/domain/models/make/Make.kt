package com.dsrcorporation.domain.models.make

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Make(
    @PrimaryKey
    val Make_ID: Int,
    val Make_Name: String
)