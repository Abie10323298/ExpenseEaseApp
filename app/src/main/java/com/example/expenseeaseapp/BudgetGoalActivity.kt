package com.example.expenseeaseapp

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class BudgetGoalActivity : BaseActivity() {
    private lateinit var db: AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.drawer_base)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        initNavigationDrawer(toolbar)

        val container = findViewById<FrameLayout>(R.id.container)
        layoutInflater.inflate(R.layout.activity_budget_goal, container, true)

        db = AppDatabase.getDatabase(this)

        val minInput = container.findViewById<EditText>(R.id.minBudgetInput)
        val maxInput = container.findViewById<EditText>(R.id.maxBudgetInput)
        val salaryInput = container.findViewById<EditText>(R.id.salaryInput)
        val monthInput = container.findViewById<EditText>(R.id.monthInput)
        val saveBtn = container.findViewById<Button>(R.id.saveBudgetBtn)

        val calendar = Calendar.getInstance()
        val monthFormat = SimpleDateFormat("yyyy-MM", Locale.getDefault())


        monthInput.apply {
            isFocusable = false
            isClickable = true
            setOnClickListener {
                val year = calendar.get(Calendar.YEAR)
                val month = calendar.get(Calendar.MONTH)

                val datePicker = DatePickerDialog(
                    this@BudgetGoalActivity,
                    { _, selectedYear, selectedMonth, _ ->
                        val selectedCalendar = Calendar.getInstance()
                        selectedCalendar.set(selectedYear, selectedMonth, 1)
                        val formattedMonth = monthFormat.format(selectedCalendar.time)
                        monthInput.setText(formattedMonth)
                    },
                    year, month, 1
                )

                // Hide the day picker (optional cosmetic)
                try {
                    val dayField = datePicker.datePicker.findViewById<View>(
                        resources.getIdentifier("day", "id", "android")
                    )
                    dayField?.visibility = View.GONE
                } catch (e: Exception) {
                    e.printStackTrace()
                }

                datePicker.show()
            }
        }

        saveBtn.setOnClickListener {
            val min = minInput.text.toString().toDoubleOrNull()
            val max = maxInput.text.toString().toDoubleOrNull()
            val salary = salaryInput.text.toString().toDoubleOrNull()
            val month = monthInput.text.toString()

            if (min == null || max == null || salary == null || month.isBlank()) {
                Toast.makeText(this, "Please enter valid values", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val goal = BudgetGoal(
                userId = 1,
                minAmount = min,
                maxAmount = max,
                salary = salary,
                month = month
            )

            lifecycleScope.launch {
                db.budgetGoalDao().insertGoal(goal)
                Toast.makeText(this@BudgetGoalActivity, "Budget goal saved", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
