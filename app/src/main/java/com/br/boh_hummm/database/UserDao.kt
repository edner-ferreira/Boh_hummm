package com.br.boh_hummm.database

import android.content.ContentValues
import com.br.boh_hummm.model.User

class UserDao(private val dbHelper: DatabaseHelper) {

    fun addUser(user: User): Long {
        val db = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put("name", user.name)
            put("email", user.email)
            put("password", user.password)
        }

        val result = db.insert("users", null, values)
        db.close()
        return result
    }

    fun getUser(email: String, password: String): User? {
        val db = dbHelper.readableDatabase
        val query = "SELECT * FROM users WHERE email = ? AND password = ?"
        val cursor = db.rawQuery(query, arrayOf(email, password))

        return if (cursor.moveToFirst()) {
            val user = User(
                id = cursor.getLong(0),
                name = cursor.getString(1),
                email = cursor.getString(2),
                password = cursor.getString(3)
            )
            cursor.close()
            user
        } else {
            cursor.close()
            null
        }
    }

    fun getUserByEmail(email: String): User? {
        val db = dbHelper.readableDatabase
        val query = "SELECT * FROM users WHERE email = ?"
        val cursor = db.rawQuery(query, arrayOf(email))

        return if (cursor.moveToFirst()) {
            val user = User(
                id = cursor.getLong(0),
                name = cursor.getString(1),
                email = cursor.getString(2),
                password = cursor.getString(3)
            )
            cursor.close()
            user
        } else {
            cursor.close()
            null
        }
    }

    fun getUserByID(id: Long): User? {
        val db = dbHelper.readableDatabase
        val query = "SELECT * FROM users WHERE id = ?"
        val cursor = db.rawQuery(query, arrayOf(id.toString()))

        return if (cursor.moveToFirst()) {
            val user = User(
                id = cursor.getLong(0),
                name = cursor.getString(1),
                email = cursor.getString(2),
                password = cursor.getString(3)
            )
            cursor.close()
            user
        } else {
            cursor.close()
            null
        }
    }
}