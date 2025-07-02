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
            put("del_time", delivery.del_time)
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
                    del_time = cursor.getString(4),
                    del_slo_id = cursor.getLong(5),
                    del_user_id = cursor.getLong(6),
                    del_mot_id = cursor.getLong(7)
                )
            )
        }
        cursor.close()
        return list
    }

    fun getDeliveriesByDateDay(userId: Long, date: String): List<Delivery> {
        val db = dbHelper.readableDatabase
        val query = """
            SELECT d.* FROM delivery d
            INNER JOIN slope s ON d.del_slo_id = s.slo_id
            WHERE d.del_user_id = ? AND s.slo_date = ?
            """.trimIndent()
        val cursor = db.rawQuery(query, arrayOf(userId.toString(), date))
        val deliveries = mutableListOf<Delivery>()

        while (cursor.moveToNext()) {
            val delivery = Delivery(
                del_id = cursor.getLong(0),
                del_order = cursor.getInt(1),
                del_fee = cursor.getDouble(2),
                del_date = cursor.getString(3),
                del_time = cursor.getString(4),
                del_slo_id = cursor.getLong(5),
                del_user_id = cursor.getLong(6),
                del_mot_id = cursor.getLong(7)
            )
            deliveries.add(delivery)
        }
        cursor.close()
        return deliveries
    }

    fun getDeliveriesByDateMonthYear(userId: Long, dateInicial: String, dateFinal: String): List<Delivery> {
        val db = dbHelper.readableDatabase
        val query = """
            SELECT d.* FROM delivery d
            INNER JOIN slope s ON d.del_slo_id = s.slo_id
            WHERE d.del_user_id = ? AND s.slo_date BETWEEN ? AND ?
        """.trimIndent()

        val cursor = db.rawQuery(query, arrayOf(userId.toString(), dateInicial, dateFinal))
        val deliveries = mutableListOf<Delivery>()

        while (cursor.moveToNext()) {
            val delivery = Delivery(
                del_id = cursor.getLong(0),
                del_order = cursor.getInt(1),
                del_fee = cursor.getDouble(2),
                del_date = cursor.getString(3),
                del_time = cursor.getString(4),
                del_slo_id = cursor.getLong(5),
                del_user_id = cursor.getLong(6),
                del_mot_id = cursor.getLong(7)
            )
            deliveries.add(delivery)
        }
        cursor.close()
        return deliveries
    }
}
