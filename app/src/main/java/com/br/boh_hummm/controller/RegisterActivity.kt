package com.br.boh_hummm.controller

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.br.boh_hummm.R
import com.br.boh_hummm.model.User

class RegisterActivity : AppCompatActivity() {

    private lateinit var userController: UserController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        userController = UserController(this)

        val etName = findViewById<EditText>(R.id.etName)
        val etEmail = findViewById<EditText>(R.id.etEmail)
        val etPassword = findViewById<EditText>(R.id.etPassword)
        val btnRegister = findViewById<Button>(R.id.btnRegister)

        btnRegister.setOnClickListener {
            val user = User(name = etName.text.toString(), email = etEmail.text.toString(), password = etPassword.text.toString())

            if (userController.registerUser(user)) {
                Toast.makeText(this, "Cadastro realizado!", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this, LoginActivity::class.java))
                finish()
            } else {
                Toast.makeText(this, "Erro: Email já cadastrado", Toast.LENGTH_SHORT).show()
            }
        }
    }
}