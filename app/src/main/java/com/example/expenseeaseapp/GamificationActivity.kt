package com.example.expenseeaseapp

import android.os.Bundle
import android.widget.*
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class GamificationActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.drawer_base)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        initNavigationDrawer(toolbar)

        val container = findViewById<FrameLayout>(R.id.container)
        layoutInflater.inflate(R.layout.activity_gamification, container, true)

        val pointsText = container.findViewById<TextView>(R.id.pointsText)
        val levelText = container.findViewById<TextView>(R.id.levelText)
        val streakText = container.findViewById<TextView>(R.id.streakText)
        val lastDateText = container.findViewById<TextView>(R.id.lastDateText)
        val badgeList = container.findViewById<ListView>(R.id.badgeList)

        val db = AppDatabase.getDatabase(this)

        lifecycleScope.launch {
            val data = withContext(Dispatchers.IO) {//
                db.gamificationDao().getGamificationData(1)
            }

            if (data != null) {
                pointsText.text = " ${data.points}"
                levelText.text = " ${data.level}"
                streakText.text = "${data.streak} days"
                lastDateText.text = "${data.lastExpenseDate}"

                val badgeData = mutableListOf<String>()
                if (data.points >= 10) badgeData.add("🏅 10 Points Badge")
                if (data.points >= 50) badgeData.add("🎖️ 50 Points Badge")
                if (data.points >= 100) badgeData.add("🥇 100 Points Badge")
                if (data.streak >= 3) badgeData.add("🔥 3 Day Streak")
                if (data.streak >= 7) badgeData.add("🏆 7 Day Streak")

                val badgeAdapter = ArrayAdapter(
                    this@GamificationActivity,
                    android.R.layout.simple_list_item_1,
                    badgeData
                )
                badgeList.adapter = badgeAdapter
            } else {
                pointsText.text = " 0"
                levelText.text = "0"
                streakText.text = "0"
                lastDateText.text = "N/A"
                badgeList.adapter = ArrayAdapter(
                    this@GamificationActivity,
                    android.R.layout.simple_list_item_1,
                    listOf("No badges earned yet.")
                )
            }
        }
    }
}
