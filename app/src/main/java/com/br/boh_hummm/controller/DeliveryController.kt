package com.br.boh_hummm.controller

import android.content.Context
import com.br.boh_hummm.database.DatabaseHelper
import com.br.boh_hummm.database.DeliveryDao
import com.br.boh_hummm.model.Delivery

class DeliveryController(context: Context) {

    private val deliveryDao = DeliveryDao(DatabaseHelper(context))

    fun registerDelivery(delivey: Delivery):Boolean {
        if (deliveryDao.insert(delivey) == null) {
            return false
        }
        return true
    }

    fun getAllDeliveriesByUserDate(userId: Long, dataFormatada: String): List<Delivery> {
        return deliveryDao.getDeliveriesByDate(userId, dataFormatada)
    }


}
