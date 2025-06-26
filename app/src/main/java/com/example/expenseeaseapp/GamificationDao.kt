package com.example.expenseeaseapp


import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface GamificationDao {

    @Query("SELECT * FROM gamification_data WHERE userId = :userId LIMIT 1")
    suspend fun getGamificationData(userId: Int): GamificationData?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertStats(data: GamificationData)
}

