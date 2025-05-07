package com.br.boh_hummm.database

import android.content.ContentValues
import android.database.sqlite.SQLiteDatabase
import com.br.boh_hummm.model.Delivery

class DeliveryDao(private val db: SQLiteDatabase) {

    fun insert(delivery: Delivery): Boolean {
        val values = ContentValues().apply {
            put("del_order", delivery.del_order)
            put("del_fee", delivery.del_fee)
            put("del_slo_id", delivery.del_slo_id)
            put("del_use_id", delivery.del_use_id)
            put("del_mot_id", delivery.del_mot_id)
        }
        return db.insert("delivery", null, values) > 0
    }

    fun getByUser(userId: Int): List<Delivery> {
        val list = mutableListOf<Delivery>()
        val cursor = db.rawQuery("SELECT * FROM delivery WHERE del_use_id = ?", arrayOf(userId.toString()))
        while (cursor.moveToNext()) {
            list.add(
                Delivery(
                    del_id = cursor.getInt(0),
                    del_order = cursor.getInt(1),
                    del_fee = cursor.getDouble(2),
                    del_slo_id = cursor.getInt(3),
                    del_use_id = cursor.getInt(4),
                    del_mot_id = cursor.getInt(5)
                )
            )
        }
        cursor.close()
        return list
    }
}
