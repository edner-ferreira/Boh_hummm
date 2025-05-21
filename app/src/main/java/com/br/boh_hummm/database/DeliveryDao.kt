package com.br.boh_hummm.database

import android.content.ContentValues
import com.br.boh_hummm.model.Delivery

class DeliveryDao(private val dbHelper: DatabaseHelper) {

    fun insert(delivery: Delivery): Boolean {
        val db = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put("del_order", delivery.del_order)
            put("del_fee", delivery.del_fee)
            put("del_date", delivery.del_date)
            put("del_slo_id", delivery.del_slo_id)
            put("del_user_id", delivery.del_user_id)
            put("del_mot_id", delivery.del_mot_id)
        }
        return db.insert("delivery", null, values) > 0
    }

    fun getByUser(userId: Int): List<Delivery> {
        val db = dbHelper.writableDatabase
        val list = mutableListOf<Delivery>()
        val cursor = db.rawQuery("SELECT * FROM delivery WHERE del_use_id = ?", arrayOf(userId.toString()))
        while (cursor.moveToNext()) {
            list.add(
                Delivery(
                    del_id = cursor.getLong(0),
                    del_order = cursor.getInt(1),
                    del_fee = cursor.getDouble(2),
                    del_date = cursor.getString(3),
                    del_slo_id = cursor.getLong(4),
                    del_user_id = cursor.getLong(5),
                    del_mot_id = cursor.getLong(6)
                )
            )
        }
        cursor.close()
        return list
    }
}
