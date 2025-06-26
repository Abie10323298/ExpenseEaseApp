package com.example.expenseeaseapp

import android.content.Intent
import android.os.Bundle
import android.widget.FrameLayout
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class CategoryListActivity : BaseActivity() {
    private lateinit var viewModel: CategoryViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.drawer_base)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        initNavigationDrawer(toolbar)

        val container = findViewById<FrameLayout>(R.id.container)
        layoutInflater.inflate(R.layout.activity_category_list, container, true)

        val recyclerView = container.findViewById<RecyclerView>(R.id.recyclerViewCategories)
        recyclerView.layoutManager = LinearLayoutManager(this)

        viewModel = ViewModelProvider(this)[CategoryViewModel::class.java]

        viewModel.categories.observe(this) { categories ->
            recyclerView.adapter = CategoryAdapter(categories) { categoryToDelete ->
                viewModel.delete(categoryToDelete)
            }
        }
        val fabAdd = container.findViewById<FloatingActionButton>(R.id.fabAddCat)
        fabAdd.setOnClickListener {
            startActivity(Intent(this, CategoryActivity::class.java)) // or MainActivity
        }

    }
}
