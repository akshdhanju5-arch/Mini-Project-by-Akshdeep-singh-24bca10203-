package com.example.myapplication

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "TaxiAppDB"
        private const val DATABASE_VERSION = 3
        
        // Users Table
        private const val TABLE_USERS = "users"
        private const val COLUMN_USER_ID = "id"
        private const val COLUMN_EMAIL = "email"
        private const val COLUMN_PASSWORD = "password"

        // Rides Table
        private const val TABLE_RIDES = "rides"
        private const val COLUMN_RIDE_ID = "ride_id"
        private const val COLUMN_PICKUP = "pickup"
        private const val COLUMN_DESTINATION = "destination"
        private const val COLUMN_CAR_TYPE = "car_type"
        private const val COLUMN_FARE = "fare"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val createUsers = ("CREATE TABLE $TABLE_USERS (" +
                "$COLUMN_USER_ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "$COLUMN_EMAIL TEXT," +
                "$COLUMN_PASSWORD TEXT)")
        
        val createRides = ("CREATE TABLE $TABLE_RIDES (" +
                "$COLUMN_RIDE_ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "$COLUMN_PICKUP TEXT," +
                "$COLUMN_DESTINATION TEXT," +
                "$COLUMN_CAR_TYPE TEXT," +
                "$COLUMN_FARE TEXT)")
        
        db?.execSQL(createUsers)
        db?.execSQL(createRides)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_USERS")
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_RIDES")
        onCreate(db)
    }

    // Auth functions
    fun addUser(email: String, pass: String): Long {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_EMAIL, email)
            put(COLUMN_PASSWORD, pass)
        }
        val result = db.insert(TABLE_USERS, null, values)
        db.close()
        return result
    }

    fun checkUser(email: String, pass: String): Boolean {
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM $TABLE_USERS WHERE $COLUMN_EMAIL=? AND $COLUMN_PASSWORD=?", arrayOf(email, pass))
        val exists = cursor.count > 0
        cursor.close()
        return exists
    }

    // Ride functions
    fun bookRide(pickup: String, dest: String, car: String, fare: String): Long {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_PICKUP, pickup)
            put(COLUMN_DESTINATION, dest)
            put(COLUMN_CAR_TYPE, car)
            put(COLUMN_FARE, fare)
        }
        val result = db.insert(TABLE_RIDES, null, values)
        db.close()
        return result
    }

    fun getAllRides(): List<String> {
        val list = mutableListOf<String>()
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM $TABLE_RIDES", null)
        if (cursor.moveToFirst()) {
            do {
                val id = cursor.getInt(0)
                val p = cursor.getString(1)
                val d = cursor.getString(2)
                val c = cursor.getString(3)
                val f = cursor.getString(4)
                list.add("ID: $id | From: $p\nTo: $d\nCar: $c | Fare: $f")
            } while (cursor.moveToNext())
        }
        cursor.close()
        return list
    }

    fun updateDestination(id: String, newDest: String): Int {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_DESTINATION, newDest)
        }
        val result = db.update(TABLE_RIDES, values, "$COLUMN_RIDE_ID=?", arrayOf(id))
        db.close()
        return result
    }

    fun cancelRide(id: String): Int {
        val db = this.writableDatabase
        val result = db.delete(TABLE_RIDES, "$COLUMN_RIDE_ID=?", arrayOf(id))
        db.close()
        return result
    }
}
