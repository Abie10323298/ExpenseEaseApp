package com.example.expenseeaseapp

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "gamification_data")
data class GamificationData(
    @PrimaryKey val userId: Int,
    var points: Int = 0,
    var level: Int = 1,
    var streak: Int = 0,
    var lastExpenseDate: String? = null
)
