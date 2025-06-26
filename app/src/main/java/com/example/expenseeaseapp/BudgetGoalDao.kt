package com.example.expenseeaseapp

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface BudgetGoalDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGoal(goal: BudgetGoal)

    @Query("SELECT * FROM budget_goals WHERE month = :month AND userId = :userId")
    suspend fun getGoalForMonth(month: String, userId: Int): BudgetGoal?

    @Query("SELECT * FROM budget_goals WHERE userId = :userId LIMIT 1")
    fun getGoalForUser(userId: Int): BudgetGoal?

    @Update
    fun updateGoal(goal: BudgetGoal)
}