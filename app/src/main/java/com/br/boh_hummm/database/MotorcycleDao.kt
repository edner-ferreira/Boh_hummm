package com.br.boh_hummm.database

import android.content.ContentValues
import android.database.sqlite.SQLiteDatabase
import com.br.boh_hummm.model.Motorcycle

class MotorcycleDao(private val db: SQLiteDatabase) {

    fun insert(moto: Motorcycle): Boolean {
        val values = ContentValues().apply {
            put("mot_brand", moto.mot_brand)
            put("mot_type", moto.mot_type)
            put("mot_cylinder_capacity", moto.mot_cylinder_capacity)
            put("mot_use_id", moto.mot_use_id)
        }
        return db.insert("motorcycle", null, values) > 0
    }

    fun getByUser(userId: Int): List<Motorcycle> {
        val list = mutableListOf<Motorcycle>()
        val cursor = db.rawQuery("SELECT * FROM motorcycle WHERE mot_use_id = ?", arrayOf(userId.toString()))
        while (cursor.moveToNext()) {
            list.add(
                Motorcycle(
                    mot_id = cursor.getInt(0),
                    mot_brand = cursor.getString(1),
                    mot_type = cursor.getString(2),
                    mot_cylinder_capacity = cursor.getDouble(3),
                    mot_use_id = cursor.getInt(4)
                )
            )
        }
        cursor.close()
        return list
    }
}

