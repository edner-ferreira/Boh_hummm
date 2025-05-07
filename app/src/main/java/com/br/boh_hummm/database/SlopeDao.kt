package com.br.boh_hummm.database

import android.content.ContentValues
import android.database.sqlite.SQLiteDatabase
import com.br.boh_hummm.model.Slope

class SlopeDao(private val db: SQLiteDatabase) {

    fun insert(slope: Slope): Boolean {
        val values = ContentValues().apply {
            put("slo_date", slope.slo_date)
            put("slo_value", slope.slo_value)
            put("slo_use_id", slope.slo_use_id)
        }
        return db.insert("slope", null, values) > 0
    }

    fun getByUser(userId: Int): List<Slope> {
        val list = mutableListOf<Slope>()
        val cursor = db.rawQuery("SELECT * FROM slope WHERE slo_use_id = ?", arrayOf(userId.toString()))
        while (cursor.moveToNext()) {
            list.add(
                Slope(
                    slo_id = cursor.getInt(0),
                    slo_date = cursor.getString(1),
                    slo_value = cursor.getDouble(2),
                    slo_use_id = cursor.getInt(3)
                )
            )
        }
        cursor.close()
        return list
    }
}
