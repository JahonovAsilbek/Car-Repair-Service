package com.dsrcorporation.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.dsrcorporation.data.local.entity.Order
import com.dsrcorporation.domain.models.make.Make
import com.dsrcorporation.domain.models.models.Model
import kotlinx.coroutines.flow.Flow

@Dao
interface VehicleDao {

    /**************************  methods for make  **************************/
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMake(makes: List<Make>)

    @Query("select * from make")
    fun getAllMakes(): Flow<List<Make>>

    /**************************  methods for models  **************************/
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertModels(models: List<Model>)

    @Query("select * from model where makeID:=makeID")
    fun getModelsByMakeID(makeID: Int): Flow<List<Model>>

    /**************************  methods for order  **************************/
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertOrder(order: Order)

    @Query("select * from order")
    fun getAllOrders(): Flow<List<Order>>

    @Query("select * from order where id:=id")
    fun getOrderByID(id: Int): Flow<Order>
}