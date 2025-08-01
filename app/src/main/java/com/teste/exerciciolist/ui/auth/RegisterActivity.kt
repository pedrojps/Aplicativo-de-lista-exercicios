package com.teste.exerciciolist.ui.auth


import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.teste.exerciciolist.MainActivity
import com.teste.exerciciolist.R
import com.teste.exerciciolist.databinding.ActivityRegisterBinding
import com.teste.exerciciolist.utils.ScreenManager
import com.teste.exerciciolist.viewmodel.AuthViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private val viewModel: AuthViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupObservers()
    }

    private fun setupObservers() {
        binding.btnCadastro.setOnClickListener {
            onClickCadastro()
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
        binding.erro.visibility = if (msg.isNullOrEmpty()) View.GONE else View.VISIBLE
        binding.erro.text = msg
    }

    private fun onClickCadastro(){
        val email = binding.email.text.toString()
        val senha = binding.senha.text.toString()

        if (senha.length < 6)
            binding.senha.error = getString(R.string.erro_cadastro)
        else
            viewModel.register(email, senha)
    }
}
