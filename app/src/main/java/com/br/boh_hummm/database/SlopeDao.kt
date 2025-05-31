package com.br.boh_hummm.database

import android.content.ContentValues
import com.br.boh_hummm.model.Slope

class SlopeDao(private val dbHelper: DatabaseHelper) {

    fun insert(slope: Slope): Boolean {
        val db = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put("slo_date", slope.slo_date)
            put("slo_time", slope.slo_time)
            put("slo_value", slope.slo_value)
            put("slo_user_id", slope.slo_user_id)
        }
        return db.insert("slope", null, values) > 0
    }

    fun getByUser(userId: Int): List<Slope> {
        val db = dbHelper.writableDatabase
        val list = mutableListOf<Slope>()
        val cursor = db.rawQuery("SELECT * FROM slope WHERE slo_user_id = ?", arrayOf(userId.toString()))
        while (cursor.moveToNext()) {
            list.add(
                Slope(
                    slo_id = cursor.getLong(0),
                    slo_date = cursor.getString(1),
                    slo_time = cursor.getString(2),
                    slo_value = cursor.getDouble(3),
                    slo_user_id = cursor.getLong(4)
                )
            )
        }
        cursor.close()
        return list
    }

    fun getSlopeLastDate(id: Long): Slope? {
        val db = dbHelper.readableDatabase
        val query = "SELECT * FROM slope WHERE slo_user_id = ? ORDER BY slo_date DESC LIMIT 1"
        val cursor = db.rawQuery(query, arrayOf(id.toString()))

        val slope = if (cursor.moveToFirst()) {
            Slope(
                slo_id = cursor.getLong(0),
                slo_date = cursor.getString(1),
                slo_time = cursor.getString(2),
                slo_value = cursor.getDouble(3),
                slo_user_id = cursor.getLong(4)
            )
        } else {
            null
        }
        cursor.close()
        return slope
    }

    fun getSlopeByUsertDateDay(id: Long, dateSlope: String): Slope? {
        val db = dbHelper.readableDatabase
        val query = """SELECT * FROM slope WHERE slo_user_id = ? AND slo_date = ?""".trimIndent()

        return db.rawQuery(query, arrayOf(id.toString(), dateSlope)).use { cursor ->
            if (cursor.moveToFirst()) {
                Slope(
                    slo_id = cursor.getLong(cursor.getColumnIndexOrThrow("slo_id")),
                    slo_date = cursor.getString(cursor.getColumnIndexOrThrow("slo_date")),
                    slo_time = cursor.getString(cursor.getColumnIndexOrThrow("slo_time")),
                    slo_value = cursor.getDouble(cursor.getColumnIndexOrThrow("slo_value")),
                    slo_user_id = cursor.getLong(cursor.getColumnIndexOrThrow("slo_user_id"))
                )
            } else {
                null
            }
        }
    }

    fun getSlopeByUsertDateMonth(userId: Long, dateInicialSlope: String, dateFinalSlope: String): List<Slope> {
        val db = dbHelper.readableDatabase
        val query = """SELECT * FROM slope 
            WHERE slo_user_id = ? AND slo_date BETWEEN ? AND ?
            """.trimIndent()

        val cursor = db.rawQuery(query, arrayOf(userId.toString(), dateInicialSlope, dateFinalSlope))

        val slopes = mutableListOf<Slope>()

        while (cursor.moveToNext()) {
            val slope = Slope (
                slo_id = cursor.getLong(0),
                slo_date = cursor.getString(1),
                slo_time = cursor.getString(2),
                slo_value = cursor.getDouble(3),
                slo_user_id = cursor.getLong(4)
            )
            slopes.add(slope)
        }
        cursor.close()
        return slopes
    }
}
