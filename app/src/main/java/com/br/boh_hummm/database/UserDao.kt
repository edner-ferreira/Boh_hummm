package com.br.boh_hummm.database

import android.content.ContentValues
import com.br.boh_hummm.model.User

class UserDao(private val dbHelper: DatabaseHelper) {

    fun addUser(user: User): Long {
        val db = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put("user_name", user.user_name)
            put("user_email", user.user_email)
            put("user_password", user.user_password)
        }

        val result = db.insert("users", null, values)
        db.close()
        return result
    }

    fun getUser(email: String, password: String): User? {
        val db = dbHelper.readableDatabase
        val query = "SELECT * FROM users WHERE user_email = ? AND user_password = ?"
        val cursor = db.rawQuery(query, arrayOf(email, password))

        return if (cursor.moveToFirst()) {
            val user = User(
                user_id = cursor.getLong(0),
                user_name = cursor.getString(1),
                user_email = cursor.getString(2),
                user_password = cursor.getString(3)
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
        val query = "SELECT * FROM users WHERE user_email = ?"
        val cursor = db.rawQuery(query, arrayOf(email))

        return if (cursor.moveToFirst()) {
            val user = User(
                user_id = cursor.getLong(0),
                user_name = cursor.getString(1),
                user_email = cursor.getString(2),
                user_password = cursor.getString(3)
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
        val query = "SELECT * FROM users WHERE user_id = ?"
        val cursor = db.rawQuery(query, arrayOf(id.toString()))

        return if (cursor.moveToFirst()) {
            val user = User(
                user_id = cursor.getLong(0),
                user_name = cursor.getString(1),
                user_email = cursor.getString(2),
                user_password = cursor.getString(3)
            )
            cursor.close()
            user
        } else {
            cursor.close()
            null
        }
    }
}