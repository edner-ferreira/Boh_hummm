package com.br.boh_hummm.database

import android.content.ContentValues
import com.br.boh_hummm.model.User

class UserDao(private val dbHelper: DatabaseHelper) {

    fun addUser(user: User): Long {
        val db = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put("user_name", user.user_name)
            put("user_email", user.user_email)
            put("user_ativo", user.user_ativo)
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
                user_ativo = cursor.getInt(3),
                user_password = cursor.getString(4)
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
                user_ativo = cursor.getInt(3),
                user_password = cursor.getString(4)
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
                user_ativo = cursor.getInt(3),
                user_password = cursor.getString(4)
            )
            cursor.close()
            user
        } else {
            cursor.close()
            null
        }
    }

    fun updateUser(user: User): Boolean {
        val db = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put("user_name", user.user_name)
            put("user_email", user.user_email)
            put("user_password", user.user_password)
        }

        val linhasAfetadas = db.update("users", values, "user_id = ?", arrayOf(user.user_id.toString()))
        db.close()
        return linhasAfetadas > 0
    }

    fun deleteUser(id: Long): Boolean {
        val db = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put("user_ativo", 0) // Usar 0 para "false" (desativado)
        }

        val linhasAfetadas = db.update("users", values, "user_id = ?", arrayOf(id.toString()))
        db.close()
        return linhasAfetadas > 0
    }
}