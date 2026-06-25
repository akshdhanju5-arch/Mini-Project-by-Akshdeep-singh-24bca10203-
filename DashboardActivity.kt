package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.card.MaterialCardView
import com.google.android.material.textfield.TextInputEditText

class DashboardActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        val dbHelper = DatabaseHelper(this)
        
        val etPickup = findViewById<TextInputEditText>(R.id.etPickup)
        val etDestination = findViewById<TextInputEditText>(R.id.etDestination)
        val rgCarType = findViewById<RadioGroup>(R.id.rgCarType)
        val btnBookRide = findViewById<Button>(R.id.btnBookRide)

        val cardHistory = findViewById<MaterialCardView>(R.id.cardHistory)
        val cardUpdate = findViewById<MaterialCardView>(R.id.cardUpdate)
        val cardDelete = findViewById<MaterialCardView>(R.id.cardDelete)
        val cardLogout = findViewById<MaterialCardView>(R.id.cardLogout)

        btnBookRide.setOnClickListener {
            val pickup = etPickup.text.toString()
            val dest = etDestination.text.toString()
            val selectedId = rgCarType.checkedRadioButtonId
            val rb = findViewById<RadioButton>(selectedId)
            val parts = rb.text.toString().split("\n")
            
            // Handle 2 lines or 3 lines of text
            val car = if (parts.size >= 2) parts[0] + " " + parts[1] else parts[0]
            val fare = if (parts.size >= 3) parts[2] else if (parts.size == 2) parts[1] else "₹0"

            if (pickup.isNotEmpty() && dest.isNotEmpty()) {
                val result = dbHelper.bookRide(pickup, dest, car, fare)
                if (result != -1L) {
                    Toast.makeText(this, "🚕 Ride Booked Successfully!", Toast.LENGTH_SHORT).show()
                    etPickup.text?.clear()
                    etDestination.text?.clear()
                } else {
                    Toast.makeText(this, "Booking Failed", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Please fill in locations", Toast.LENGTH_SHORT).show()
            }
        }

        cardHistory.setOnClickListener {
            startActivity(Intent(this, ViewEntriesActivity::class.java))
        }

        cardUpdate.setOnClickListener {
            startActivity(Intent(this, UpdatePasswordActivity::class.java))
        }

        cardDelete.setOnClickListener {
            startActivity(Intent(this, DeleteAccountActivity::class.java))
        }

        cardLogout.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        // Set a default location (e.g., Delhi, India) and move the camera
        val defaultLocation = LatLng(28.6139, 77.2090)
        mMap.addMarker(MarkerOptions().position(defaultLocation).title("Marker in Delhi"))
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(defaultLocation, 12f))
        
        mMap.uiSettings.isZoomControlsEnabled = true
    }
}
