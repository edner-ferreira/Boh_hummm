package com.br.boh_hummm.activity

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.br.boh_hummm.R
import com.br.boh_hummm.controller.DeliveryController
import com.br.boh_hummm.controller.MotorcycleController
import com.br.boh_hummm.controller.SessionManager
import com.br.boh_hummm.controller.SlopeController
import com.br.boh_hummm.model.Delivery
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter

class InserirDeliveryActivity : AppCompatActivity() {

    private lateinit var deliveryController: DeliveryController
    private lateinit var sessionManager: SessionManager
    private lateinit var slopeController: SlopeController
    private lateinit var motorcycleController: MotorcycleController

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_inserir_delivery)

        deliveryController = DeliveryController(this)
        sessionManager = SessionManager(this)
        slopeController = SlopeController(this)
        motorcycleController = MotorcycleController(this)

        val spinnerMotos = findViewById<Spinner>(R.id.spinnerMoto)
        val etComanda = findViewById<EditText>(R.id.etComanda)
        val etTaxa = findViewById<EditText>(R.id.etTaxa)
        val btnInserirEntrega = findViewById<Button>(R.id.btnInserirEntrega)
        val btnVoltar = findViewById<Button>(R.id.btnVoltar)

        val userId = sessionManager.getUserId().toLong()
        val motos = MotorcycleController(this).getMotosDoUsuario(userId)

        if (motos.isNotEmpty()) {
            val adapter = ArrayAdapter(
                this,
                android.R.layout.simple_spinner_item,
                motos.map { "${it.mot_brand} ${it.mot_type}" }
            )
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinnerMotos.adapter = adapter
        } else {
            Toast.makeText(this, "Nenhuma moto cadastrada. Cadastre uma moto primeiro.", Toast.LENGTH_LONG).show()
            btnInserirEntrega.isEnabled = false // opcional: desativa o botão
        }

        btnInserirEntrega.setOnClickListener {
            val comanda = etComanda.text.toString().trim()
            val taxa = etTaxa.text.toString().trim()
            val dataAtual = LocalDate.now()
            val horaAtual = LocalTime.now()
            val formatadorDate = DateTimeFormatter.ofPattern("yyyy-MM-dd")
            val formatadorTime = DateTimeFormatter.ofPattern("HH:mm")
            val dataFormatada = dataAtual.format(formatadorDate)
            val horaFormatada = horaAtual.format(formatadorTime)

            if (comanda.isEmpty() || taxa.isEmpty()) {
                Toast.makeText(this, "Preencha o campo corretamente", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val slope = slopeController.buscarUltimoSlopeInserido(userId, dataFormatada)
            val slopeId = slope?.slo_id ?: run {
                Toast.makeText(this, "Nenhum slope encontrado para este usuário.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val delivery = Delivery(
                del_order = comanda.toString().toInt(),
                del_fee = taxa.toString().toDoubleOrNull(),
                del_date = dataFormatada,
                del_time = horaFormatada,
                del_slo_id = slopeId,
                del_user_id = userId,
                del_mot_id = userId
            )

            val deliveyInserido = deliveryController.registerDelivery(delivery)
            if (deliveyInserido) {
                Toast.makeText(this, "Inserção de entrega realizada!", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this, ProfileActivity::class.java))
                finish()
            } else {
                Toast.makeText(this, "Erro: Não foi possivel inserir a entrega", Toast.LENGTH_SHORT).show()
            }
        }

        btnVoltar.setOnClickListener {
            startActivity(Intent(this, ProfileActivity::class.java))
            finish()
        }
    }
}