package com.br.boh_hummm.controller

import android.content.Context
import com.br.boh_hummm.database.DatabaseHelper
import com.br.boh_hummm.database.UserDao
import com.br.boh_hummm.model.User

class UserController(context: Context) {

    private val userDao = UserDao(DatabaseHelper(context))
    private lateinit var userdb: User

    fun registerUser(user: User): Long {
        if (userDao.getUserByEmail(user.user_email) != null) {
            return 0 // Email já cadastrado
        }
        return userDao.addUser(user)
    }

    fun login(email: String, password: String): User? {
        return userDao.getUser(email, password)
    }

    fun getUserID(id: Long): User? {
        return userDao.getUserByID(id)
    }
}