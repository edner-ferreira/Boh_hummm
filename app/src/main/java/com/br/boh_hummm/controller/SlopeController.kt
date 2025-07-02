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

    fun buscarUltimoSlopeInserido(userId: Long, dateSlope: String): Slope? {
        val slope = slopeDao.getSlopeLastDate(userId, dateSlope)
        if (slope == null) {
            return null
        }
        return slope
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun verificarSlopeDiariaInserida(userId: Long, dateSlope: String): Boolean {
        val slope = slopeDao.getSlopeLastDate(userId, dateSlope)
        if (slope == null) {
            return false
        }
        val ultimaDataInserida = slope.slo_date
        val dataHoje = LocalDate.now()
        val formatadorDate = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        val dataFormatada = dataHoje.format(formatadorDate).toString()

        if (ultimaDataInserida.equals(dataFormatada)) {
            return true
        }
        return false
    }

    fun buscarValueSlopeByUserDateDay(userId: Long, dateSlope: String): Slope? {
        val slope = slopeDao.getSlopeByUsertDateDay(userId, dateSlope)
        if (slope == null) {
            return null
        }
        return slope
    }

    fun buscarValueSlopeByUserDateMonth(userId: Long, dateInicialSlope: String, dateFinalSlope: String): List<Slope> {
        return slopeDao.getSlopeByUsertDateMonthYear(userId, dateInicialSlope, dateFinalSlope)
    }
}
