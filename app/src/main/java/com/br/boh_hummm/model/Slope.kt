package com.br.boh_hummm.model

data class Slope(
    val slo_id: Int = 0,
    val slo_date: String,
    val slo_value: Double?,
    val slo_mot_id: Int // chave estrangeira para Motorcycle
)