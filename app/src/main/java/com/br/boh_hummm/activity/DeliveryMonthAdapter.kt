package com.br.boh_hummm.activity

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.br.boh_hummm.R
import com.br.boh_hummm.model.Delivery

class DeliveryMonthAdapter(private val deliveries: List<Delivery>) :
    RecyclerView.Adapter<DeliveryMonthAdapter.DeliveryViewHolder>() {

    class DeliveryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvComandaTaxa: TextView = itemView.findViewById(R.id.tvComandaTaxa)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DeliveryViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.one_month_delivery, parent, false)
        return DeliveryViewHolder(view)
    }

    override fun onBindViewHolder(holder: DeliveryViewHolder, position: Int) {
        val delivery = deliveries[position]
        holder.tvComandaTaxa.text = "Comanda:${delivery.del_order} | Taxa R$%.2f | Time:${delivery.del_date}-${delivery.del_time}".format(delivery.del_fee ?: 0.0)
    }

    override fun getItemCount(): Int = deliveries.size
}
