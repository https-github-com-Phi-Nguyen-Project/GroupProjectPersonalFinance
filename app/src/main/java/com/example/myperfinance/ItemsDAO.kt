package com.example.myperfinance

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow


@Dao
interface ItemsDAO {

    // Query to get all data from the table transactions data
    @Query("SELECT * FROM TransactionData")
    fun getAll(): Flow<List<Items>>

    // Insert new item to the transaction table
    @Insert
    fun insertAll(vararg item:Items)

    // Delete all data from transaction table
    @Query("DELETE FROM TransactionData")
    fun deleteAll()

    // Delete single item from table
    @Delete
    fun deleteItem(item: Items)


    // Get the lowest vlue
    // @Query("SELECT MIN(Calories) FROM HealthData")
    //fun getLowest()

    // Get the average value
    //@Query("SELECT AVG(Calories) FROM HealthData")
    //fun getAvg()

}