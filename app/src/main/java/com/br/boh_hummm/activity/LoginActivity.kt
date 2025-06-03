package com.br.boh_hummm.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.br.boh_hummm.MainActivity
import com.br.boh_hummm.R
import com.br.boh_hummm.controller.SessionManager
import com.br.boh_hummm.controller.UserController

class LoginActivity : AppCompatActivity() {

    private lateinit var userController: UserController
    private lateinit var sessionManager: SessionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        userController = UserController(this)
        sessionManager = SessionManager(this)

        val etEmail = findViewById<EditText>(R.id.etEmail)
        val etPassword = findViewById<EditText>(R.id.etPassword)
        val btnLogin = findViewById<Button>(R.id.btnLogin)
        val btnVoltar = findViewById<Button>(R.id.btnVoltar)

        btnLogin.setOnClickListener {
            val email = etEmail.text.toString()
            val password = etPassword.text.toString()

            val user = userController.login(email, password)
            if (user != null) {
                if (user.user_ativo != 0) {
                    sessionManager.saveUserSession(user.user_id)  // 🔹 Salva o ID do usuário logado
                    val intent = Intent(this, ProfileActivity::class.java)
                    intent.putExtra("USER_ID", user.user_id)
                    startActivity(intent)
                    finish()
                } else {
                    Toast.makeText(this, "Usuário inativo!!!", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Email ou senha incorretos, \n" +
                        "ou usuário não cadastrado", Toast.LENGTH_SHORT).show()
            }
        }

        btnVoltar.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }
}