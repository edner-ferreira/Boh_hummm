package com.br.boh_hummm.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.br.boh_hummm.R
import com.br.boh_hummm.controller.MotorcycleController
import com.br.boh_hummm.controller.UserController
import com.br.boh_hummm.model.Motorcycle
import com.br.boh_hummm.model.User

class RegisterActivity : AppCompatActivity() {

    private lateinit var userController: UserController
    private lateinit var motorcycleController: MotorcycleController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        userController = UserController(this)
        motorcycleController = MotorcycleController(this)

        val etName = findViewById<EditText>(R.id.etName)
        val etEmail = findViewById<EditText>(R.id.etEmail)
        val etPassword = findViewById<EditText>(R.id.etPassword)
        val etBrand = findViewById<EditText>(R.id.etBrand)
        val etType = findViewById<EditText>(R.id.etType)
        val etCylinder = findViewById<EditText>(R.id.etCylinder)
        val btnRegister = findViewById<Button>(R.id.btnRegister)

        btnRegister.setOnClickListener {
            val name = etName.text.toString().trim()
            val email = etEmail.text.toString().trim()
            val password = etPassword.text.toString()
            val brand = etBrand.text.toString().trim()
            val type = etType.text.toString().trim()
            val cylinder = etCylinder.text.toString().toDoubleOrNull()

            if (name.isEmpty() || email.isEmpty() || password.isEmpty()
                || brand.isEmpty() || type.isEmpty() || cylinder == null) {
                Toast.makeText(this, "Preencha todos os campos corretamente", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val user = User(
                name = name,
                email = email,
                password = password
            )
            val userId = userController.registerUser(user)

            if (userId > 0) {
                val motorcycle = Motorcycle(
                    mot_brand = brand,
                    mot_type = type,
                    mot_cylinder_capacity = cylinder,
                    mot_use_id = userId
                )

                val motoInserido = motorcycleController.registerUserWithMotorcycle(motorcycle)
                if (motoInserido) {
                    Toast.makeText(this, "Cadastro realizado!", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this, LoginActivity::class.java))
                    finish()
                }
            } else {
                Toast.makeText(this, "Erro: Email já cadastrado", Toast.LENGTH_SHORT).show()
            }
        }
    }
}