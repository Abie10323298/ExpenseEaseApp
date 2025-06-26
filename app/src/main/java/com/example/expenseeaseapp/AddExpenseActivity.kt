package com.example.expenseeaseapp

import android.os.Bundle
import android.widget.*
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.sql.Date
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class AddExpenseActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.drawer_base)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        initNavigationDrawer(toolbar)

        // Find and inflate container from drawer_base
        val container = findViewById<FrameLayout>(R.id.container)
        layoutInflater.inflate(R.layout.activity_add_expense, container, true)

        val db = AppDatabase.getDatabase(this)

        //  Access views from the inflated layout via container
        val descriptionInput = container.findViewById<EditText>(R.id.descriptionInput)
        val amountInput = container.findViewById<EditText>(R.id.amountInput)

        val startTimeInput = container.findViewById<EditText>(R.id.startTimeInput)
        val endTimeInput = container.findViewById<EditText>(R.id.endTimeInput)
        val categorySpinner = container.findViewById<Spinner>(R.id.categorySpinner)
        val saveButton = container.findViewById<Button>(R.id.saveExpenseBtn)

        val dateInput = container.findViewById<EditText>(R.id.dateInput)

        val calendar = Calendar.getInstance()
        val dateFormat = java.text.SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

        dateInput.setOnClickListener {
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val day = calendar.get(Calendar.DAY_OF_MONTH)

            val datePickerDialog = android.app.DatePickerDialog(
                this,
                { _, selectedYear, selectedMonth, selectedDay ->
                    calendar.set(selectedYear, selectedMonth, selectedDay)
                    val selectedDate = dateFormat.format(calendar.time)
                    dateInput.setText(selectedDate)
                },
                year, month, day
            )
            datePickerDialog.show()
        }
        fun showTimePicker(targetEditText: EditText) {
            val calendar = Calendar.getInstance()
            val hour = calendar.get(Calendar.HOUR_OF_DAY)
            val minute = calendar.get(Calendar.MINUTE)

            val timePicker = android.app.TimePickerDialog(
                this,
                { _, selectedHour, selectedMinute ->
                    val formattedTime = String.format("%02d:%02d", selectedHour, selectedMinute)
                    targetEditText.setText(formattedTime)
                },
                hour,
                minute,
                true
            )
            timePicker.show()
        }

        startTimeInput.setOnClickListener {
            showTimePicker(startTimeInput)
        }

        endTimeInput.setOnClickListener {
            showTimePicker(endTimeInput)
        }




        // 🔄 Load categories and populate the spinner
        lifecycleScope.launch {
            val categories = withContext(Dispatchers.IO) {
                db.categoryDao().getCategoryList()
            }

            if (categories.isEmpty()) {
                Toast.makeText(this@AddExpenseActivity, "No categories found. Please add categories first.", Toast.LENGTH_LONG).show()
                return@launch
            }

            val categoryNames = categories.map { it.name }
            val adapter = ArrayAdapter(
                this@AddExpenseActivity,
                android.R.layout.simple_spinner_dropdown_item,
                categoryNames
            )
            categorySpinner.adapter = adapter

            saveButton.setOnClickListener {
                val description = descriptionInput.text.toString()
                val amount = amountInput.text.toString().toDoubleOrNull() ?: 0.0
                val date = dateInput.text.toString()
                val startTime = startTimeInput.text.toString()
                val endTime = endTimeInput.text.toString()

                val selectedCategory = categories[categorySpinner.selectedItemPosition]

                if (description.isNotBlank() && date.isNotBlank() && startTime.isNotBlank() && endTime.isNotBlank()) {
                    val expense = Expense(
                        description = description,
                        amount = amount,
                        date = date,
                        startTime = startTime,
                        endTime = endTime,
                        categoryId = selectedCategory.id
                    )

                    lifecycleScope.launch {
                        withContext(Dispatchers.IO) {
                            db.expenseDao().insertExpense(expense)

                            // GAMIFICATION LOGIC
                            val gamificationDao = db.gamificationDao()
                            val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                            val today = sdf.format(java.util.Date())

                            val userId = 1 // or use actual logged-in user ID
                            val stats = gamificationDao.getGamificationData(userId) ?: GamificationData(userId = userId)

                            if (stats.lastExpenseDate != today) {
                                val yesterday = Calendar.getInstance().apply { add(Calendar.DAY_OF_YEAR, -1) }
                                val lastDate = stats.lastExpenseDate?.let { sdf.parse(it) }
                                stats.streak = if (lastDate != null && sdf.format(lastDate) == sdf.format(yesterday.time)) {
                                    stats.streak + 1
                                } else {
                                    1
                                }
                                stats.points = maxOf(0, stats.points + 10) // Prevents going below zero
                                stats.level = 1 + stats.points / 100
                                stats.lastExpenseDate = today
                                gamificationDao.insertStats(stats)
                            }
                        }
                        Toast.makeText(this@AddExpenseActivity, "Expense added successfully", Toast.LENGTH_SHORT).show()
                        //clear text
                        descriptionInput.text.clear()
                        amountInput.text.clear()
                        dateInput.text.clear()
                        startTimeInput.text.clear()
                        endTimeInput.text.clear()
                        categorySpinner.setSelection(0)
                    }
                } else {
                    Toast.makeText(this@AddExpenseActivity, "Please fill in all required fields", Toast.LENGTH_SHORT).show()
                }
            }

        }

    }
}
