package com.example.expenseeaseapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        val usernameField = findViewById<EditText>(R.id.username)
        val emailField = findViewById<EditText>(R.id.email)
        val passwordField = findViewById<EditText>(R.id.password)
        val registerButton = findViewById<Button>(R.id.registerBtn)
        val loginBtn = findViewById<Button>(R.id.loginBtn)

        val db = AppDatabase.getDatabase(this)

        loginBtn.setOnClickListener()
        {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }

        registerButton.setOnClickListener {
            val user = User(
                username = usernameField.text.toString(),
                email = emailField.text.toString(),
                password = passwordField.text.toString()
            )

            if (user.username.isNotEmpty() && user.email.isNotEmpty() && user.password.isNotEmpty()) {
                db.userDao().insert(user)
                Toast.makeText(this, "Registered Successfully!", Toast.LENGTH_SHORT).show()
                // Go to LoginActivity
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
                //v finish()
            } else {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
