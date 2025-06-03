package com.br.boh_hummm.model

data class User(
    val user_id: Long = 0,
    val user_name: String,
    val user_email: String,
    val user_ativo: Int,
    val user_password: String
)
