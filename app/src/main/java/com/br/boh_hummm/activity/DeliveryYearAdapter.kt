package com.br.boh_hummm.activity

import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.br.boh_hummm.R
import com.br.boh_hummm.model.MesEntregasTaxas

class DeliveryYearAdapter(private val mesEntregasTaxas: List<MesEntregasTaxas>) :
    RecyclerView.Adapter<DeliveryYearAdapter.DeliveryViewHolder>() {

    class DeliveryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvMesTaxa: TextView = itemView.findViewById(R.id.tvMesTaxa)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DeliveryViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.one_year_delivery, parent, false)
        return DeliveryViewHolder(view)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: DeliveryViewHolder, position: Int) {
        val mesTaxa = mesEntregasTaxas[position]
        holder.tvMesTaxa.text = "Mês: ${mesTaxa.mes} | Entregas: ${mesTaxa.qtdEntregas} | Taxas: R$ %.2f".format(mesTaxa.totalTaxas)
    }

    override fun getItemCount(): Int = mesEntregasTaxas.size
}
