package com.dsrcorporation.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.dsrcorporation.domain.models.make.Make
import com.dsrcorporation.domain.models.models.Model
import com.dsrcorporation.domain.models.order.Order
import kotlinx.coroutines.flow.Flow

@Dao
interface VehicleDao {

    /**************************  methods for make  **************************/
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMake(makes: List<Make>)

    @Query("select * from make")
    fun getAllMakes(): Flow<List<Make>>

    /**************************  methods for models  **************************/
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertModels(models: List<Model>)

    @Query("select * from model where makeID=:makeID")
    fun getModelsByMakeID(makeID: Int): Flow<List<Model>>

    /**************************  methods for order  **************************/
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrder(order: Order)

    @Query("select * from repair_order")
    fun getAllOrders(): Flow<List<Order>>

    @Query("select * from repair_order where isClosed=:isClosed")
    fun getAllOrders(isClosed: Boolean): Flow<List<Order>>

    @Query("select * from repair_order where isClosed=:isClosed")
    fun getOrderByStatus(isClosed: Boolean): Flow<List<Order>>

    @Query("select * from repair_order where id=:id")
    fun getOrderByID(id: Int): Flow<Order>

    @Query("update repair_order set isClosed=:isClosed where id=:id")
    suspend fun changeOrderStatus(isClosed: Boolean, id: Int)

    @Query("select * from repair_order where isClosed=:isClosed order by model")
    fun getOrderByModel(isClosed: Boolean): Flow<List<Order>>

    @Query("select * from repair_order where isClosed=:isClosed order by model desc")
    fun getOrderByModelDesc(isClosed: Boolean): Flow<List<Order>>

    @Query("select * from repair_order where isClosed=:isClosed order by date")
    fun getOrderByDate(isClosed: Boolean): Flow<List<Order>>

    @Query("select * from repair_order where isClosed=:isClosed order by date desc")
    fun getOrderByDateDesc(isClosed: Boolean): Flow<List<Order>>

    @Query("select * from repair_order order by model")
    fun getOrderByModel(): Flow<List<Order>>

    @Query("select * from repair_order order by model desc")
    fun getOrderByModelDesc(): Flow<List<Order>>

    @Query("select * from repair_order order by date")
    fun getOrderByDate(): Flow<List<Order>>

    @Query("select * from repair_order order by date desc")
    fun getOrderByDateDesc(): Flow<List<Order>>


}