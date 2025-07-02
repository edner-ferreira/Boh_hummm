package com.br.boh_hummm.activity

import android.annotation.SuppressLint
import android.content.Intent
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
import com.br.boh_hummm.model.MesEntregasTaxas
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class ListarEntregasAnoActivity : AppCompatActivity() {

    private lateinit var deliveryController: DeliveryController
    private lateinit var slopeController: SlopeController
    private lateinit var sessionManager: SessionManager
    private lateinit var recyclerView: RecyclerView

    private lateinit var tvEntregas: TextView
    private lateinit var tvEncostas: TextView
    private lateinit var tvTaxas: TextView
    private lateinit var tvTaxasComEncostas: TextView

    @SuppressLint("MissingInflatedId")
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_listar_delivery_anual)

        deliveryController = DeliveryController(this)
        slopeController = SlopeController(this)
        sessionManager = SessionManager(this)

        recyclerView = findViewById(R.id.rcAllDeliveryList)
        tvEntregas = findViewById(R.id.tvEntrega)
        tvEncostas = findViewById(R.id.tvEncosta)
        tvTaxas = findViewById(R.id.tvTaxa)
        tvTaxasComEncostas = findViewById(R.id.tvTaxaComEncosta)

        val userId = sessionManager.getUserId().toLong()
        val dateAtual = LocalDate.now()
        val formatadorDate = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        val dataFormatada = dateAtual.format(formatadorDate).toString()
        val dataMesAno = dataFormatada.split("-")
        val dataInicial =  dataMesAno[0] + "-" + "01" + "-" + "01"
        val btnVoltar = findViewById<Button>(R.id.btnVolta)

        val lista = deliveryController.getAllDeliveriesByUserDateMonth(userId, dataInicial, dataFormatada)
        var slope = slopeController.buscarValueSlopeByUserDateMonth(userId, dataInicial, dataFormatada)

        val formatadorMesAno = DateTimeFormatter.ofPattern("MM/yyyy")
        val taxasPorMes = lista
            .filter { it.del_date != null }
            .groupBy {
                LocalDate.parse(it.del_date, formatadorDate).format(formatadorMesAno)
            }
            .map { (mes, entregas) ->
                val totalTaxa = entregas.sumOf { it.del_fee ?: 0.0 }
                val totalEntregas = entregas.size
                MesEntregasTaxas(mes, totalTaxa, totalEntregas)
            }
            .sortedBy { it.mes } // ordenar por mês

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = DeliveryYearAdapter(taxasPorMes)

        val totalEntregas = lista.size
        val totalTaxas = lista.sumOf { it.del_fee ?: 0.0 }
        val totalSlopeValues = slope.sumOf { it.slo_value ?: 0.0 }
        val totalTaxasEncosta = totalTaxas + totalSlopeValues

        tvEntregas.text = "Total de entregas no ano: $totalEntregas"
        tvEncostas.text = "Total de encostas no ano: R$ %.2f".format(totalSlopeValues)
        tvTaxas.text = "Total de taxas S encosta no ano: R$ %.2f".format(totalTaxas)
        tvTaxasComEncostas.text = "Total de taxas C encosta no ano: R$ %.2f".format(totalTaxasEncosta)

        btnVoltar.setOnClickListener {
            startActivity(Intent(this, ProfileActivity::class.java))
            finish()
        }
    }
}
