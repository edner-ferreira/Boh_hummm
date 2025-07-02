package com.br.boh_hummm.activity

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.br.boh_hummm.MainActivity
import com.br.boh_hummm.R
import com.br.boh_hummm.controller.SessionManager
import com.br.boh_hummm.controller.SlopeController
import com.br.boh_hummm.controller.UserController
import java.time.LocalDate
import java.time.format.DateTimeFormatter

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
            val btnRelatorioDiario = findViewById<Button>(R.id.btnRelatorioDiario)
            val btnRelatorioMensal = findViewById<Button>(R.id.btnRelatorioMensal)
            val btnRelatorioAnual = findViewById<Button>(R.id.btnRelatorioAnual)
            val btnEditarPerfil = findViewById<Button>(R.id.btnEditarPerfil)
            val btnDeletarPerfil = findViewById<Button>(R.id.btnDeletarPerfil)
            val btnLogout = findViewById<Button>(R.id.btnLogout)
            val dateAtual = LocalDate.now()
            val formatadorDate = DateTimeFormatter.ofPattern("yyyy-MM-dd")
            val dataFormatada = dateAtual.format(formatadorDate).toString()

            val slopeDiaria = slopeController.verificarSlopeDiariaInserida(userId, dataFormatada)

            if (slopeDiaria == true) {
//                Toast.makeText(this, "Encosta já cadastrada neste dia.", Toast.LENGTH_LONG).show()
                btnInserirEncosta.isVisible = false // opcional: desativa o botão
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

            btnRelatorioDiario.setOnClickListener {
                startActivity(Intent(this, ListarEntregasDiaActivity::class.java))
                finish()
            }

            btnRelatorioMensal.setOnClickListener {
                val intent = Intent(this, ListarEntregasMesActivity::class.java)
                startActivity(intent)
                finish()
            }

            btnRelatorioAnual.setOnClickListener {
                val intent = Intent(this, ListarEntregasAnoActivity::class.java)
                startActivity(intent)
                finish()
            }

            btnEditarPerfil.setOnClickListener {
                val intent = Intent(this, EditarRegisterActivity::class.java)
                startActivity(intent)
                finish()
            }

            btnDeletarPerfil.setOnClickListener {
                if (userController.deleteUser(userId)) {
                    Toast.makeText(this, "Usuário deletado com sucesso.", Toast.LENGTH_LONG).show()
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    Toast.makeText(this, "Erro ao deletar usuário.", Toast.LENGTH_LONG).show()
                }
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