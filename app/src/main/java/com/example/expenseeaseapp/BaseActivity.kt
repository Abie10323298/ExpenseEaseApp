package com.example.expenseeaseapp

import android.content.Intent
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.example.expenseeaseapp.DashboardActivity
import com.example.expenseeaseapp.R
import com.google.android.material.navigation.NavigationView

open class BaseActivity : AppCompatActivity() {
    lateinit var drawerLayout: DrawerLayout
    lateinit var navView: NavigationView

    fun initNavigationDrawer(toolbar: Toolbar) {
        drawerLayout = findViewById(R.id.drawerLayout)
        navView = findViewById(R.id.navigationView)

        val toggle = ActionBarDrawerToggle(
            this, drawerLayout, toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        navView.setNavigationItemSelectedListener { item ->
            val currentClass = this::class.java

            when (item.itemId) {
                R.id.nav_dashboard -> if (currentClass != DashboardActivity::class.java) {
                    startActivity(Intent(this, DashboardActivity::class.java))
                }
                R.id.nav_add_expense -> if (currentClass != AddExpenseActivity::class.java) {
                    startActivity(Intent(this, AddExpenseActivity::class.java))
                }
                R.id.nav_add_category -> if (currentClass != CategoryActivity::class.java) {
                    startActivity(Intent(this, CategoryActivity::class.java))
                }
                R.id.nav_view_expenses -> if (currentClass != ViewExpenseActivity::class.java) {
                    startActivity(Intent(this, ViewExpenseActivity::class.java))
                }
                R.id.nav_budget_goal -> if (currentClass != BudgetGoalActivity::class.java) {
                    startActivity(Intent(this, BudgetGoalActivity::class.java))
                }
                R.id.nav_view_category -> if (currentClass != CategoryListActivity::class.java) {
                    startActivity(Intent(this, CategoryListActivity::class.java))
                }
                R.id.nav_gamification -> if (currentClass != GamificationActivity::class.java) {
                    startActivity(Intent(this, GamificationActivity::class.java))
                }
                R.id.nav_learning -> {
                    startActivity(Intent(this, LearningActivity::class.java))
                }



                // Optional logout
                R.id.nav_logout -> {
                    startActivity(Intent(this, LoginActivity::class.java))
                    finish()
                }
            }

            drawerLayout.closeDrawer(GravityCompat.START)
            true
        }
    }
}
