package com.br.boh_hummm.activity

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.br.boh_hummm.R
import com.br.boh_hummm.controller.DeliveryController
import com.br.boh_hummm.controller.SessionManager
import com.br.boh_hummm.controller.SlopeController
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class ListarEntregasDiaActivity : AppCompatActivity() {

    private lateinit var deliveryController: DeliveryController
    private lateinit var slopeController: SlopeController
    private lateinit var sessionManager: SessionManager
    private lateinit var recyclerView: RecyclerView

    private lateinit var tvEntrega: TextView
    private lateinit var tvTaxa: TextView
    private lateinit var tvTaxaComEncosta: TextView

    @SuppressLint("MissingInflatedId")
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_listar_delivery)

        deliveryController = DeliveryController(this)
        slopeController = SlopeController(this)
        sessionManager = SessionManager(this)

        recyclerView = findViewById(R.id.rcAllDeliveryList)
        tvEntrega = findViewById(R.id.tvEntrega)
        tvTaxa = findViewById(R.id.tvTaxa)
        tvTaxaComEncosta = findViewById(R.id.tvTaxaComEncosta)

        val userId = sessionManager.getUserId().toLong()
        val dateAtual = LocalDate.now()
        val formatadorDate = DateTimeFormatter.ofPattern("dd/MM/yyyy")
        val dataFormatada = dateAtual.format(formatadorDate).toString()
        val btnVoltar = findViewById<Button>(R.id.btnVoltar)

        val lista = deliveryController.getAllDeliveriesByUserDate(userId, dataFormatada)
        var slope = slopeController.buscarValueSlopeByUserDate(userId, dataFormatada)
        var slopeValue = slope?.slo_value

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = DeliveryAdapter(lista)

        val totalEntregas = lista.size
        val totalTaxas = lista.sumOf { it.del_fee ?: 0.0 }
        val totalTaxasEncosta = totalTaxas + (slopeValue?.toDouble() ?: 0.0)

        tvEntrega.text = "Total de entregas: $totalEntregas"
        tvTaxa.text = "Total de taxas S encosta: R$ %.2f".format(totalTaxas)
        tvTaxaComEncosta.text = "Total de taxas C encosta: R$ %.2f".format(totalTaxasEncosta)

//        btnVoltar.setOnClickListener {
//            startActivity(Intent(this, InserirDeliveryActivity::class.java))
//            finish()
//        }
    }
}