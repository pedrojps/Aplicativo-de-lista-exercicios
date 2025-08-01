package com.teste.exerciciolist.ui.auth

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.teste.exerciciolist.MainActivity
import com.teste.exerciciolist.data.firebase.AuthManager
import com.teste.exerciciolist.databinding.ActivityLoginBinding
import com.teste.exerciciolist.utils.ScreenManager
import com.teste.exerciciolist.viewmodel.AuthViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private val viewModel: AuthViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (AuthManager.isLoggedIn()) {
            startActivity(Intent(this, MainActivity::class.java))
            finish() 
            return
        }

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupObservers()
    }

    private fun setupObservers(){
        binding.btnLogin.setOnClickListener {
            onClickLogin()
        }

        binding.btnCadastro.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }

        viewModel.authSuccess.observe(this) {
            if (it) {
                ScreenManager.toGoMain(this)
                finish()
            }
        }

        viewModel.authError.observe(this) {
            setErrorMessage(it)
        }
    }

    private fun setErrorMessage(msg: String?) {
        binding.erro.visibility = if (msg.isNullOrEmpty())View.GONE else View.VISIBLE
        binding.erro.text = msg
    }

    private fun onClickLogin(){
        val email = binding.email.text.toString()
        val senha = binding.senha.text.toString()
        viewModel.login(email, senha)
    }
}