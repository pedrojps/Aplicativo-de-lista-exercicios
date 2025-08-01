package com.teste.exerciciolist.ui.auth

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.teste.exerciciolist.MainActivity
import com.teste.exerciciolist.databinding.ActivityLoginBinding
import com.teste.exerciciolist.viewmodel.AuthViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private val viewModel: AuthViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnLogin.setOnClickListener {
            val email = binding.email.text.toString()
            val senha = binding.senha.text.toString()
            viewModel.login(email, senha)
        }

        binding.btnCadastro.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }

        viewModel.authSuccess.observe(this) {
            if (it) {
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }
        }

        viewModel.authError.observe(this) {
            it?.let { msg -> Toast.makeText(this, msg, Toast.LENGTH_LONG).show() }
        }
    }
}