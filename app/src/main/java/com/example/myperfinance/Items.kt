package com.example.myperfinance

import java.io.Serializable
import java.util.*

class Items (var id: Int, var value: Float, var title: String, var isExpense: Boolean,
             var date: Date, var desc: String, var category: Int): Serializable