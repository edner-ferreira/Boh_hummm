package com.br.boh_hummm.activity

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.br.boh_hummm.R
import com.br.boh_hummm.model.Delivery

class DeliveryAdapter(private val deliveries: List<Delivery>) :
    RecyclerView.Adapter<DeliveryAdapter.DeliveryViewHolder>() {

    class DeliveryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvComanda: TextView = itemView.findViewById(R.id.tvComanda)
        val tvTaxa: TextView = itemView.findViewById(R.id.tvTaxa)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DeliveryViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.one_delivery, parent, false)
        return DeliveryViewHolder(view)
    }

    override fun onBindViewHolder(holder: DeliveryViewHolder, position: Int) {
        val delivery = deliveries[position]
        holder.tvComanda.text = "Comanda: ${delivery.del_order}"
        holder.tvTaxa.text = "R$ %.2f".format(delivery.del_fee ?: 0.0)
    }

    override fun getItemCount(): Int = deliveries.size
}
