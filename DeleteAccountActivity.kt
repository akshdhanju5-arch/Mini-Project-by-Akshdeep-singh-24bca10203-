package com.example.myapplication

import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText

class DeleteAccountActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_delete_account)

        val dbHelper = DatabaseHelper(this)
        val deleteEmail = findViewById<TextInputEditText>(R.id.deleteEmail)
        val btnDoDelete = findViewById<Button>(R.id.btnDoDelete)
        val btnBack = findViewById<Button>(R.id.btnBackFromDelete)

        btnDoDelete.setOnClickListener {
            val id = deleteEmail.text.toString()

            if (id.isNotEmpty()) {
                val result = dbHelper.cancelRide(id)
                if (result > 0) {
                    Toast.makeText(this, "Ride Cancelled Successfully", Toast.LENGTH_SHORT).show()
                    finish()
                } else {
                    Toast.makeText(this, "Ride ID not found", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Please enter Ride ID to cancel", Toast.LENGTH_SHORT).show()
            }
        }

        btnBack.setOnClickListener {
            finish()
        }
    }
}
