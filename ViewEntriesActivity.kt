package com.example.myapplication

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity

class ViewEntriesActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_entries)

        val dbHelper = DatabaseHelper(this)
        val usersListView = findViewById<ListView>(R.id.usersListView)
        val btnBack = findViewById<Button>(R.id.btnBack)

        val rides = dbHelper.getAllRides()
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, rides)
        usersListView.adapter = adapter

        btnBack.setOnClickListener {
            finish()
        }
    }
}
