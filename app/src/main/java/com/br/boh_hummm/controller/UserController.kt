package com.br.boh_hummm.controller

import android.content.Context
import com.br.boh_hummm.database.DatabaseHelper
import com.br.boh_hummm.database.UserDao
import com.br.boh_hummm.model.User

class UserController(context: Context) {

    private val userDao = UserDao(DatabaseHelper(context))
    private lateinit var userdb: User

    fun registerUser(user: User): Boolean {
        if (userDao.getUserByEmail(user.email) != null) {
            return false // Email já cadastrado
        }
        return userDao.addUser(user)
    }

    fun login(email: String, password: String): User? {
        return userDao.getUser(email, password)
    }

    fun getUserID(id: Int): User? {
        return userDao.getUserByID(id)
    }
}