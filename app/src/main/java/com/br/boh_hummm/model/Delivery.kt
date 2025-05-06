package com.br.boh_hummm.model

data class Delivery(
    val del_id: Int = 0,
    val del_order: Int,
    val del_fee: Double?,
    val del_delr_id: Int // chave estrangeira para DeliveryRoute
)
