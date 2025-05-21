package com.br.boh_hummm.activity

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.br.boh_hummm.MainActivity
import com.br.boh_hummm.R
import com.br.boh_hummm.controller.SessionManager
import com.br.boh_hummm.controller.SlopeController
import com.br.boh_hummm.controller.UserController

class ProfileActivity : AppCompatActivity() {

    private lateinit var userController: UserController
    private lateinit var slopeController: SlopeController
    private lateinit var sessionManager: SessionManager

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        userController = UserController(this)
        slopeController = SlopeController(this)
        sessionManager = SessionManager(this)

        val userId = sessionManager.getUserId().toLong()
        if (sessionManager.isLoggedIn()) {
            val user = userController.getUserID(userId)
            findViewById<TextView>(R.id.tvUserName).text = "Nome: ${user?.user_name}"
            findViewById<TextView>(R.id.tvUserEmail).text = "Email: ${user?.user_email}"

            val btnInserirEncosta = findViewById<Button>(R.id.btnInserirEncosta)
            val btnInserirEntrega = findViewById<Button>(R.id.btnInserirEntrega)
            val btnRelatorioMensal = findViewById<Button>(R.id.btnRelatorioMensal)
            val btnLogout = findViewById<Button>(R.id.btnLogout)

            val slopeDiaria = slopeController.verificarSlopeDiariaInserida(userId)

            if (slopeDiaria == true) {
//                Toast.makeText(this, "Encosta já cadastrada neste dia.", Toast.LENGTH_LONG).show()
                btnInserirEncosta.isEnabled = false // opcional: desativa o botão
            }
            btnInserirEncosta.setOnClickListener {
                val intent = Intent(this, InserirSlopeActivity::class.java)
                startActivity(intent)
                finish()
            }

            btnInserirEntrega.setOnClickListener {
                val intent = Intent(this, InserirDeliveryActivity::class.java)
                startActivity(intent)
                finish()
            }

            btnRelatorioMensal.setOnClickListener {
                val intent = Intent(this, ListarDeliveryActivity::class.java)
                startActivity(intent)
                finish()
            }

            btnLogout.setOnClickListener {
                sessionManager.logout() // 🔹 Remove os dados da sessão
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }
        }else {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }
}