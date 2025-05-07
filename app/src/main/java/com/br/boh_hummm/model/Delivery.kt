package com.br.boh_hummm.model

data class Delivery(
    val del_id: Int = 0,
    val del_order: Int,
    val del_fee: Double?,
    val del_slo_id: Int, // chave estrangeira para Slope
    val del_use_id: Int,  // chave estrangeira para User
    val del_mot_id: Int // chave estrangeira para Motocycle
)
