package com.br.boh_hummm.model

data class Delivery(
    val del_id: Long = 0,
    val del_order: Int,
    val del_fee: Double?,
    val del_date: String,
    val del_slo_id: Long, // chave estrangeira para Slope
    val del_user_id: Long,  // chave estrangeira para User
    val del_mot_id: Long // chave estrangeira para Motocycle
)
