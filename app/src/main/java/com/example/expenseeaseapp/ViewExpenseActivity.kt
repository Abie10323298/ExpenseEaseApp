package com.example.expenseeaseapp

import android.app.DatePickerDialog
import android.os.Bundle
import android.widget.*
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.*

class ViewExpenseActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.drawer_base)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        initNavigationDrawer(toolbar)

        val container = findViewById<FrameLayout>(R.id.container)
        layoutInflater.inflate(R.layout.activity_view_expense, container, true)

        val db = AppDatabase.getDatabase(this)
        val dateFilterInput = container.findViewById<EditText>(R.id.dateFilterInput)
        val expenseListView = container.findViewById<ListView>(R.id.expenseListView)

        val calendar = Calendar.getInstance()
        val monthFormat = SimpleDateFormat("yyyy-MM", Locale.getDefault()) // 📅 month only

        dateFilterInput.setOnClickListener {
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)

            DatePickerDialog(
                this,
                { _, selectedYear, selectedMonth, _ ->
                    // We ignore the day, only use year and month
                    calendar.set(selectedYear, selectedMonth, 1)
                    val selectedMonthStr = monthFormat.format(calendar.time)
                    dateFilterInput.setText(selectedMonthStr)
                    loadExpensesForMonth(selectedMonthStr, db, expenseListView)
                },
                year, month, calendar.get(Calendar.DAY_OF_MONTH)
            ).show()
        }

        // Load current month’s expenses by default
        val thisMonth = monthFormat.format(calendar.time)
        dateFilterInput.setText(thisMonth)
        loadExpensesForMonth(thisMonth, db, expenseListView)
    }

    private fun loadExpensesForMonth(month: String, db: AppDatabase, listView: ListView) {
        lifecycleScope.launch {
            val expenses = withContext(Dispatchers.IO) {
                db.expenseDao().getExpensesByMonth(month)
            }

            val adapter = ArrayAdapter(
                this@ViewExpenseActivity,
                android.R.layout.simple_list_item_1,
                expenses.map { "${it.date} • ${it.description} - R${"%.2f".format(it.amount)}" }
            )
            listView.adapter = adapter
        }
    }
}
