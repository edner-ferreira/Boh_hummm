package com.br.boh_hummm.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.br.boh_hummm.R
import com.br.boh_hummm.controller.SessionManager
import com.br.boh_hummm.controller.UserController
import com.br.boh_hummm.model.User

class EditarRegisterActivity : AppCompatActivity() {

    private lateinit var userController: UserController
    private lateinit var sessionManager: SessionManager


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_editar_register)

        userController = UserController(this)
        sessionManager = SessionManager(this)

        val userId = sessionManager.getUserId().toLong()
        val etName = findViewById<EditText>(R.id.etName)
        val etEmail = findViewById<EditText>(R.id.etEmail)
        val etPassword = findViewById<EditText>(R.id.etPassword)
        val btnEditar = findViewById<Button>(R.id.btnEditar)
        val btnVoltar = findViewById<Button>(R.id.btnVoltar)

        val usuario = userController.getUserID(userId)
        usuario?.let {
            etName.setText(it.user_name)
            etEmail.setText(it.user_email)
            etPassword.setText(it.user_password) // cuidado com senha salva em texto plano!
        }

        btnEditar.setOnClickListener {
            val name = etName.text.toString().trim()
            val email = etEmail.text.toString().trim()
            val password = etPassword.text.toString()

            if (name.isEmpty() || email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Preencha todos os campos corretamente", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val user = User(
                user_id = userId,
                user_name = name,
                user_email = email,
                user_ativo = 1,
                user_password = password
            )

            if (userController.updateUser(user)) {
                Toast.makeText(this, "Update de usuário realizado com sucesso!", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this, ProfileActivity::class.java))
                finish()
            } else {
                Toast.makeText(this, "Erro ao editar usuaŕio", Toast.LENGTH_SHORT).show()
            }
        }

        btnVoltar.setOnClickListener {
            startActivity(Intent(this, ProfileActivity::class.java))
            finish()
        }
    }
}