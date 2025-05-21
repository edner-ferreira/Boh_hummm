package com.br.boh_hummm.activity

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.br.boh_hummm.R
import com.br.boh_hummm.controller.SessionManager
import com.br.boh_hummm.controller.SlopeController
import com.br.boh_hummm.model.Slope
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class InserirSlopeActivity : AppCompatActivity() {

    private lateinit var slopeController: SlopeController
    private lateinit var sessionManager: SessionManager

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_inserir_slope)

        slopeController = SlopeController(this)
        sessionManager = SessionManager(this)

        val etEncosta = findViewById<EditText>(R.id.etEncosta)
        val btnInserirSlope = findViewById<Button>(R.id.btnInserirEncosta)
        val btnVoltar = findViewById<Button>(R.id.btnVoltar)

        btnInserirSlope.setOnClickListener {
            val encosta = etEncosta.text.toString().trim()

            if (encosta == null) {
                Toast.makeText(this, "Preencha o campo corretamente", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            val userId = sessionManager.getUserId().toLong()
            val dataHoraAtual = LocalDateTime.now()
            val formatador = DateTimeFormatter.ofPattern("dd/MM/yyyy-HH:mm:ss")
            val dataHoraFormatada = dataHoraAtual.format(formatador)
            val slope = Slope(
                slo_date = dataHoraFormatada,
                slo_value = encosta.toString().toDoubleOrNull(),
                slo_user_id = userId
            )

            val encostaInserida = slopeController.registerSlope(slope)
            if (encostaInserida) {
                Toast.makeText(this, "Inserção de encosta realizada!", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this, ProfileActivity::class.java))
                finish()
            } else {
                Toast.makeText(this, "Erro: Não foi possivel inserir a encosta", Toast.LENGTH_SHORT).show()
            }
        }

        btnVoltar.setOnClickListener {
            startActivity(Intent(this, ProfileActivity::class.java))
            finish()
        }
    }
}