package com.br.boh_hummm.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.os.FileObserver.CREATE

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "UserDatabase.db"
        private const val DATABASE_VERSION = 1

        private const val TABLE_USERS = "users"
        private const val TABLE_MOTORCYCLE = "motorcycle"
        private const val TABLE_SLOP = "slope"
        private const val TABLE_DELIVERY = "delivery"

        private const val COLUMN_USER_ID = "id"
        private const val COLUMN_USER_NAME = "name"
        private const val COLUMN_USER_EMAIL = "email"
        private const val COLUMN_USER_PASSWORD = "password"

        private const val COLUMN_MOT_ID = "mot_brand"
        private const val COLUMN_MOT_BRAND = "mot_brand"
        private const val COLUMN_MOT_TYPE = "mot_type"
        private const val COLUMN_MOT_CYLINDER_CAPACITY = "mot_cylinder_capacity"
        private const val COLUMN_MOT_USE_ID = "mot_use_id"

        private const val COLUMN_SLO_ID = "slo_id"
        private const val COLUMN_SLO_DATE = "slo_date"
        private const val COLUMN_SLO_VALUE = "slo_value"
        private const val COLUMN_SLO_USER_ID = "slo_user_id"

        private const val COLUMN_DEL_ID = "del_id"
        private const val COLUMN_DEL_ORDER = "del_order"
        private const val COLUMN_DEL_FEE = "del_fee"
        private const val COLUMN_DEL_SLO_ID = "del_slo_id"
        private const val COLUMN_DEL_USER_ID = "del_user_id"
        private const val COLUMN_DEL_MOT_ID = "del_mot_id"

    }

    override fun onCreate(db: SQLiteDatabase) {
        val createTableQuery = """
            CREATE TABLE $TABLE_USERS (
                $COLUMN_USER_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COLUMN_USER_NAME TEXT,
                $COLUMN_USER_EMAIL TEXT UNIQUE,
                $COLUMN_USER_PASSWORD TEXT
            );
                
            CREATE TABLE $TABLE_MOTORCYCLE (
                $COLUMN_MOT_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COLUMN_MOT_BRAND TEXT,
                $COLUMN_MOT_TYPE TEXT UNIQUE,
                $COLUMN_MOT_CYLINDER_CAPACITY REAL,
                $COLUMN_MOT_USE_ID INTEGER NOT NULL,
                FOREIGN KEY ($COLUMN_MOT_USE_ID) REFERENCES user ($COLUMN_USER_ID) ON DELETE CASCADE
            );
            
            CREATE TABLE $TABLE_SLOP (
                $COLUMN_SLO_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COLUMN_SLO_DATE DATE NOT NULL,
                $COLUMN_SLO_VALUE REAL,
                $COLUMN_SLO_USER_ID INTEGER NOT NULL,
                FOREIGN KEY ($COLUMN_SLO_USER_ID) REFERENCES user ($COLUMN_USER_ID) ON DELETE CASCADE
            );
                       
            CREATE TABLE_$TABLE_DELIVERY (
                $COLUMN_DEL_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COLUMN_DEL_ORDER INTEGER UNIQUE,
                $COLUMN_DEL_FEE REAL,
                $COLUMN_DEL_SLO_ID INTEGER NOT NULL,
                $COLUMN_DEL_USER_ID INTEGER NOT NULL,
                $COLUMN_DEL_MOT_ID INTEGER NOT NULL,
                FOREIGN KEY ($COLUMN_DEL_SLO_ID) REFERENCES slope ($COLUMN_DEL_ID) ON DELETE CASCADE,
                FOREIGN KEY ($COLUMN_DEL_USER_ID) REFERENCES user ($COLUMN_USER_ID) ON DELETE CASCADE,
                FOREIGN KEY ($COLUMN_DEL_MOT_ID) REFERENCES motorcycle ($COLUMN_MOT_ID) ON DELETE CASCADE
            );
        """.trimIndent()
        db.execSQL(createTableQuery)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_USERS")
        db.execSQL("DROP TABLE IF EXISTS $TABLE_SLOP")
        db.execSQL("DROP TABLE IF EXISTS $TABLE_MOTORCYCLE")
        db.execSQL("DROP TABLE IF EXISTS $TABLE_DELIVERY")
        onCreate(db)
    }
}