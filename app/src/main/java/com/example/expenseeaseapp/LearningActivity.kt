package com.example.expenseeaseapp

import android.os.Bundle
import android.widget.FrameLayout
import androidx.appcompat.widget.Toolbar

class LearningActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.drawer_base)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        initNavigationDrawer(toolbar)

        val container = findViewById<FrameLayout>(R.id.container)
        layoutInflater.inflate(R.layout.activity_learning, container, true)
    }
}
