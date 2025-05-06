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
        private const val TABLE_DELIVERY_ROUTE = "delivery_route"
        private const val TABLE_DELIVERY = "delivery"

        private const val COLUMN_ID = "id"
        private const val COLUMN_NAME = "name"
        private const val COLUMN_EMAIL = "email"
        private const val COLUMN_PASSWORD = "password"

        private const val COLUMN_MOT_ID = "mot_brand"
        private const val COLUMN_MOT_BRAND = "mot_brand"
        private const val COLUMN_MOT_TYPE = "mot_type"
        private const val COLUMN_MOT_CYLINDER_CAPACITY = "mot_cylinder_capacity"
        private const val COLUMN_MOT_USE_ID = "mot_use_id"

        private const val COLUMN_SLO_ID = "slo_id"
        private const val COLUMN_SLO_DATE = "slo_date"
        private const val COLUMN_SLO_VALUE = "slo_value"
        private const val COLUMN_SLO_MOT_ID = "slo_mot_id"

        private const val COLUMN_DELR_ID = "delr_id"
        private const val COLUMN_DELR_IDENTIFIER = "delr_identifier"
        private const val COLUMN_DELR_SLO_ID = "delr_slo_id"

        private const val COLUMN_DEL_ID = "del_id"
        private const val COLUMN_DEL_ORDER = "del_order"
        private const val COLUMN_DEL_FEE = "del_fee"
        private const val COLUMN_DEL_DELR_ID = "del_delr_id"

    }

    override fun onCreate(db: SQLiteDatabase) {
        val createTableQuery = """
            CREATE TABLE $TABLE_USERS (
                $COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COLUMN_NAME TEXT,
                $COLUMN_EMAIL TEXT UNIQUE,
                $COLUMN_PASSWORD TEXT
            );
                
            CREATE TABLE $TABLE_MOTORCYCLE (
                $COLUMN_MOT_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COLUMN_MOT_BRAND TEXT,
                $COLUMN_MOT_TYPE TEXT UNIQUE,
                $COLUMN_MOT_CYLINDER_CAPACITY REAL,
                $COLUMN_MOT_USE_ID INTEGER NOT NULL,
                FOREIGN KEY ($COLUMN_MOT_USE_ID) REFERENCES user (COLUMN_ID) ON DELETE CASCADE
            );
            
            CREATE TABLE $TABLE_SLOP (
                $COLUMN_SLO_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COLUMN_SLO_DATE DATE NOT NULL,
                $COLUMN_SLO_VALUE REAL,
                $COLUMN_SLO_MOT_ID INTEGER NOT NULL,
                FOREIGN KEY ($COLUMN_SLO_MOT_ID) REFERENCES user ($COLUMN_MOT_ID) ON DELETE CASCADE
            );
            
            CREATE TABLE $TABLE_DELIVERY_ROUTE (
                $COLUMN_DELR_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COLUMN_DELR_IDENTIFIER INTEGER,
                $COLUMN_DELR_SLO_ID INTEGER NOT NULL,
                FOREIGN KEY ($COLUMN_DELR_SLO_ID) REFERENCES user ($COLUMN_SLO_ID) ON DELETE CASCADE
            );
            
            CREATE TABLE_$TABLE_DELIVERY (
                $COLUMN_DEL_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COLUMN_DEL_ORDER INTEGER UNIQUE,
                $COLUMN_DEL_FEE REAL,
                $COLUMN_DEL_DELR_ID INTEGER NOT NULL,
                FOREIGN KEY ($COLUMN_DEL_DELR_ID) REFERENCES user ($COLUMN_DEL_ID) ON DELETE CASCADE
            );
        """.trimIndent()
        db.execSQL(createTableQuery)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_USERS")
        db.execSQL("DROP TABLE IF EXISTS $TABLE_DELIVERY_ROUTE")
        db.execSQL("DROP TABLE IF EXISTS $TABLE_SLOP")
        db.execSQL("DROP TABLE IF EXISTS $TABLE_MOTORCYCLE")
        db.execSQL("DROP TABLE IF EXISTS $TABLE_DELIVERY")
        onCreate(db)
    }
}