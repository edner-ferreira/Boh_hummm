package com.br.boh_hummm.model

data class Motorcycle(
    val mot_id: Int = 0,
    val mot_brand: String?,
    val mot_type: String?,
    val mot_cylinder_capacity: Double?,
    val mot_use_id: Int // chave estrangeira para User
)