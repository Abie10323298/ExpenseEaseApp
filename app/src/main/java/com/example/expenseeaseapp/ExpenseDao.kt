package com.example.expenseeaseapp

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface ExpenseDao {


    @Query("SELECT * FROM expense WHERE date = :date")
    suspend fun getExpensesByDate(date: String): List<Expense>


    @Insert
    fun insertExpense(expense: Expense)

    @Query("SELECT * FROM Expense WHERE date BETWEEN :startDate AND :endDate")
    suspend fun getExpensesBetween(startDate: String, endDate: String): List<Expense>

    @Query("""
    SELECT categories.name AS name, SUM(expense.amount) AS total 
    FROM expense 
    INNER JOIN categories ON expense.categoryId = categories.id 
    WHERE date BETWEEN :startDate AND :endDate 
    GROUP BY categoryId
""")
    fun getTotalPerCategory(startDate: String, endDate: String): List<CategoryTotal>
    @Query("SELECT * FROM expense")
    fun getAllExpenses(): List<Expense>

    @Query("SELECT * FROM Expense WHERE date LIKE :month || '%'")
    fun getExpensesByMonth(month: String): List<Expense>


}

data class CategoryTotal(val name: String, val total: Double)