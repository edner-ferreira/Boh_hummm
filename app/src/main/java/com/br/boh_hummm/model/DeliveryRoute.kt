package com.br.boh_hummm.model

data class DeliveryRoute(
    val delr_id: Int = 0,
    val delr_identifier: Int?,
    val delr_slo_id: Int // chave estrangeira para Slope
)
