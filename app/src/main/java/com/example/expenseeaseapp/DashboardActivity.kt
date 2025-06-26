package com.example.expenseeaseapp

import android.os.Bundle
import android.widget.*
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.*
import com.github.mikephil.charting.utils.ColorTemplate
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.*

class DashboardActivity : BaseActivity() {

    private val currentUserId = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.drawer_base)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        initNavigationDrawer(toolbar)

        val container = findViewById<FrameLayout>(R.id.container)
        layoutInflater.inflate(R.layout.activity_dashboard, container, true)

        val db = AppDatabase.getDatabase(this)

        val monthText = container.findViewById<TextView>(R.id.monthText)
        val initialSalaryText = container.findViewById<TextView>(R.id.initialSalaryText)
        val currentSalaryText = container.findViewById<TextView>(R.id.currentSalaryText)
        val budgetGoalText = container.findViewById<TextView>(R.id.budgetGoalText)
        val progressBar = container.findViewById<ProgressBar>(R.id.salaryProgressBar)
        val progressLabel = container.findViewById<TextView>(R.id.progressLabel)
        val pieChart = container.findViewById<PieChart>(R.id.expenseChart)

        val currentMonth = SimpleDateFormat("MMMM yyyy", Locale.getDefault()).format(Date())
        monthText.text = "Month: $currentMonth"

        lifecycleScope.launch {
            val goal = withContext(Dispatchers.IO) {
                db.budgetGoalDao().getGoalForUser(currentUserId)
            }

            val expenses = withContext(Dispatchers.IO) {
                db.expenseDao().getAllExpenses()
            }

            if (goal != null) {
                val totalExpenses = expenses.sumOf { it.amount }
                val initialSalary = goal.salary
                val remainingSalary = maxOf(0.0, initialSalary - totalExpenses)


                // Update Text
                initialSalaryText.text = " R%.2f".format(initialSalary)
                currentSalaryText.text = " R%.2f".format(remainingSalary)
                budgetGoalText.text = " Min R%.2f - Max R%.2f".format(goal.minAmount, goal.maxAmount)

                // ProgressBar setup
                val percentUsed = ((totalExpenses / initialSalary) * 100).coerceAtMost(100.0).toInt()
                progressBar.progress = percentUsed
                progressLabel.text = "$percentUsed% of your salary spent"

                // Change color based on usage
                val colorRes = when {
                    totalExpenses >= goal.maxAmount -> android.R.color.holo_red_light
                    totalExpenses >= goal.minAmount -> android.R.color.holo_orange_light
                    else -> android.R.color.holo_green_light
                }
                progressBar.progressTintList = ContextCompat.getColorStateList(this@DashboardActivity, colorRes)

                // Pie chart setup
                val categoryMap = mutableMapOf<String, Double>()
                withContext(Dispatchers.IO) {
                    expenses.forEach { expense ->
                        val category = db.categoryDao().getCategoryById(expense.categoryId)?.name ?: "Unknown"
                        categoryMap[category] = categoryMap.getOrDefault(category, 0.0) + expense.amount
                    }
                }

                val pieEntries = categoryMap.map { PieEntry(it.value.toFloat(), it.key) }
                val pieDataSet = PieDataSet(pieEntries, "Expense Categories").apply {
                    colors = ColorTemplate.MATERIAL_COLORS.toList()
                    valueTextSize = 14f
                }

                pieChart.apply {
                    data = PieData(pieDataSet)
                    description.isEnabled = false
                    centerText = "Spending by Category"
                    animateY(1000)
                    invalidate()
                }
            } else {
                // If no goal set
                initialSalaryText.text = "Monthly Salary: N/A"
                currentSalaryText.text = "Remaining: N/A"
                budgetGoalText.text = "Budget Goal: N/A"
                progressBar.progress = 0
                progressLabel.text = "No goal set"
                pieChart.clear()
            }
        }
    }
}
