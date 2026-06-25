package com.example.myapplication

import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText

class UpdatePasswordActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_password)

        val dbHelper = DatabaseHelper(this)
        val updateEmail = findViewById<TextInputEditText>(R.id.updateEmail)
        val updateNewPassword = findViewById<TextInputEditText>(R.id.updateNewPassword)
        val btnDoUpdate = findViewById<Button>(R.id.btnDoUpdate)
        val btnBack = findViewById<Button>(R.id.btnBackFromUpdate)

        btnDoUpdate.setOnClickListener {
            val id = updateEmail.text.toString()
            val newDest = updateNewPassword.text.toString()

            if (id.isNotEmpty() && newDest.isNotEmpty()) {
                val result = dbHelper.updateDestination(id, newDest)
                if (result > 0) {
                    Toast.makeText(this, "Destination Updated", Toast.LENGTH_SHORT).show()
                    finish()
                } else {
                    Toast.makeText(this, "Ride ID not found", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Please enter ID and new destination", Toast.LENGTH_SHORT).show()
            }
        }

        btnBack.setOnClickListener {
            finish()
        }
    }
}
