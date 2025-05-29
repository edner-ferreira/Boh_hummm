package com.br.boh_hummm.model

data class Slope(
    val slo_id: Long = 0,
    val slo_date: String,
    val slo_time: String,
    val slo_value: Double?,
    val slo_user_id: Long // chave estrangeira para User
)