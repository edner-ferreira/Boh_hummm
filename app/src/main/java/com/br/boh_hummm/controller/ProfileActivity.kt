package com.br.boh_hummm.controller

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.br.boh_hummm.R

class ProfileActivity : AppCompatActivity() {

    private lateinit var userController: UserController
    private lateinit var sessionManager: SessionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        userController = UserController(this)
        sessionManager = SessionManager(this)

        val userId = sessionManager.getUserId()
        if (sessionManager.isLoggedIn()) {
            val user = userController.getUserID(userId)
            findViewById<TextView>(R.id.tvUserName).text = "Nome: ${user?.name}"
            findViewById<TextView>(R.id.tvUserEmail).text = "Email: ${user?.email}"

            val btnLogout = findViewById<Button>(R.id.btnLogout)
            btnLogout.setOnClickListener {
                sessionManager.logout() // 🔹 Remove os dados da sessão
                startActivity(Intent(this, LoginActivity::class.java))
                finish()
            }
        }
    }
}