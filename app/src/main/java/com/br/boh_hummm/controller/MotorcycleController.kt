package com.br.boh_hummm.controller

import android.content.Context
import com.br.boh_hummm.database.DatabaseHelper
import com.br.boh_hummm.database.MotorcycleDao
import com.br.boh_hummm.model.Motorcycle

class MotorcycleController(context: Context) {

    private val motorcycleDao = MotorcycleDao(DatabaseHelper(context))

    fun registerUserWithMotorcycle(motorcycle: Motorcycle): Boolean {
        if (motorcycleDao.insert(motorcycle) == null) {
            return false // Erro ao cadastrar motorcycle
        }
        return true
    }

}
