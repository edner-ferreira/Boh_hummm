package com.br.boh_hummm.controller

import android.content.Context
import com.br.boh_hummm.database.DatabaseHelper
import com.br.boh_hummm.database.MotorcycleDao
import com.br.boh_hummm.database.UserDao
import com.br.boh_hummm.model.User

class MotorcycleController(context: Context) {

    private val motorcycleDao = MotorcycleDao(DatabaseHelper(context))
    private lateinit var userdb: User

    fun registerUser(user: User): Boolean {
        if (motorcycleDao.getUserByEmail(user.email) != null) {
            return false // Email já cadastrado
        }
        return motorcycleDao.addUser(user)
    }

}
