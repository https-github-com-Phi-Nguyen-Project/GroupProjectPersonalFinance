package com.example.myperfinance

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey



@Entity(tableName = "TransactionData")
class Items (

    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    @ColumnInfo(name = "Title") val title: String?,
    @ColumnInfo(name = "amount")  val amount: Double?

        )