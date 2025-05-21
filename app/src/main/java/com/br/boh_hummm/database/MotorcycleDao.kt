package com.br.boh_hummm.database

import android.content.ContentValues
import com.br.boh_hummm.model.Motorcycle

class MotorcycleDao(private val dbHelper: DatabaseHelper) {

    fun insert(moto: Motorcycle): Boolean {
        val db = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put("mot_brand", moto.mot_brand)
            put("mot_type", moto.mot_type)
            put("mot_cylinder_capacity", moto.mot_cylinder_capacity)
            put("mot_user_id", moto.mot_user_id)
        }
        return db.insert("motorcycle", null, values) > 0
    }

    fun getAllMotorcyclesByUser(userId: Long): List<Motorcycle> {
        val db = dbHelper.writableDatabase
        val list = mutableListOf<Motorcycle>()
        val cursor = db.rawQuery("SELECT * FROM motorcycle WHERE mot_user_id = ?", arrayOf(userId.toString()))
        while (cursor.moveToNext()) {
            list.add(
                Motorcycle(
                    mot_id = cursor.getLong(0),
                    mot_brand = cursor.getString(1),
                    mot_type = cursor.getString(2),
                    mot_cylinder_capacity = cursor.getDouble(3),
                    mot_user_id = cursor.getLong(4)
                )
            )
        }
        cursor.close()
        return list
    }

    fun getMotorcycleByUserID(id: Long): Motorcycle? {
        val db = dbHelper.readableDatabase
        val query = "SELECT * FROM motorcycle WHERE id = ?"
        val cursor = db.rawQuery(query, arrayOf(id.toString()))

        return if (cursor.moveToFirst()) {
            val motorcycle = Motorcycle(
                mot_id = cursor.getLong(0),
                mot_brand = cursor.getString(1),
                mot_type = cursor.getString(2),
                mot_cylinder_capacity = cursor.getDouble(3),
                mot_user_id = cursor.getLong(4)
            )
            cursor.close()
            motorcycle
        } else {
            cursor.close()
            null
        }
    }
}

