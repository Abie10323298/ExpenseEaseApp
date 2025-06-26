package com.example.expenseeaseapp

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

//this code create categories entity




@Entity(tableName = "categories")
data class Category(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String,
    val limitAmount: Double)
