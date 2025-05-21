package com.br.boh_hummm.controller

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import com.br.boh_hummm.database.DatabaseHelper
import com.br.boh_hummm.database.SlopeDao
import com.br.boh_hummm.model.Slope
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class SlopeController(context: Context) {

    private val slopeDao = SlopeDao(DatabaseHelper(context))

    fun registerSlope(slope: Slope):Boolean {
        if (slopeDao.insert(slope) == null) {
            return false
        }
        return true
    }

    fun buscarUltimoSlopeInserido(userId: Long): Slope? {
        val slope = slopeDao.getSlopeLastDate(userId)
        if (slope == null) {
            return null
        }
        return slope
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun verificarSlopeDiariaInserida(userId: Long): Boolean {
        val slope = slopeDao.getSlopeLastDate(userId)
        if (slope == null) {
            return false
        }
        val ultimaDataHoraInserida = slope.slo_date
        val ultimaData = ultimaDataHoraInserida.split("-")
        val dataHoje = LocalDate.now()
        val formatador = DateTimeFormatter.ofPattern("dd/MM/yyyy")
        val dataAtual = dataHoje.format(formatador).toString()

        if (ultimaData[0].equals(dataAtual)) {
            return true
        }
        return false
    }

}
