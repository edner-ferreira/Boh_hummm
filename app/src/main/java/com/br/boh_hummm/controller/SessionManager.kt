package com.br.boh_hummm.controller

import android.content.Context
import android.content.SharedPreferences

class SessionManager(context: Context) {

    private val sharedPref: SharedPreferences = context.getSharedPreferences("UserSession", Context.MODE_PRIVATE)

    fun saveUserSession(userId: Long) {
        val editor = sharedPref.edit()
        editor.putLong("USER_ID", userId)
        editor.putBoolean("IS_LOGGED_IN", true)
        editor.apply()
    }

    fun getUserId(): Long {
        return sharedPref.getLong("USER_ID", -1)
    }

    fun isLoggedIn(): Boolean {
        return sharedPref.getBoolean("IS_LOGGED_IN", false)
    }

    fun logout() {
        val editor = sharedPref.edit()
        editor.clear()
        editor.apply()
    }
}