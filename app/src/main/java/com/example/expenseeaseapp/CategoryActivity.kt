package com.example.expenseeaseapp

import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModelProvider

class CategoryActivity : BaseActivity() {
    private lateinit var viewModel: CategoryViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.drawer_base)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        initNavigationDrawer(toolbar)

        //  Inflate the actual content layout into the container
        val container = findViewById<FrameLayout>(R.id.container)
        layoutInflater.inflate(R.layout.activity_category, container, true)

        //  Now safely access views from the inflated layout
        val etName = container.findViewById<EditText>(R.id.etCategoryName)
        val etLimit = container.findViewById<EditText>(R.id.etCategoryLimit)
        val btnAdd = container.findViewById<Button>(R.id.btnAddCategory)

        viewModel = ViewModelProvider(this)[CategoryViewModel::class.java]

        btnAdd.setOnClickListener {
            val name = etName.text.toString()
            val limit = etLimit.text.toString().toDoubleOrNull() ?: 0.0

            if (name.isNotBlank()) {
                viewModel.insert(Category(name = name, limitAmount = limit))
                Toast.makeText(this, "$name added", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this, CategoryListActivity::class.java))
            } else {
                Toast.makeText(this, "Please enter a valid category name", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
