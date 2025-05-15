package com.br.boh_hummm.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.br.boh_hummm.R
import com.br.boh_hummm.controller.SessionManager
import com.br.boh_hummm.controller.UserController

class ProfileActivity : AppCompatActivity() {

    private lateinit var userController: UserController
    private lateinit var sessionManager: SessionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        userController = UserController(this)
        sessionManager = SessionManager(this)

        val userId = sessionManager.getUserId().toLong()
        if (sessionManager.isLoggedIn()) {
            val user = userController.getUserID(userId)
            findViewById<TextView>(R.id.tvUserName).text = "Nome: ${user?.name}"
            findViewById<TextView>(R.id.tvUserEmail).text = "Email: ${user?.email}"

            val btnInserirEntrega = findViewById<Button>(R.id.btnInserirEntrega)
            val btnRelatorioMensal = findViewById<Button>(R.id.btnRelatorioMensal)
            val btnLogout = findViewById<Button>(R.id.btnLogout)

            btnInserirEntrega.setOnClickListener {

            }

            btnLogout.setOnClickListener {
                sessionManager.logout() // 🔹 Remove os dados da sessão
                startActivity(Intent(this, LoginActivity::class.java))
                finish()
            }
        }
    }
}