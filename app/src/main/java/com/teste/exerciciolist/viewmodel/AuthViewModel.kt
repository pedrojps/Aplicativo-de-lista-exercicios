package com.teste.exerciciolist.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.teste.exerciciolist.data.firebase.AuthManager
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor() : ViewModel() {

    private val _authSuccess = MutableLiveData<Boolean>()
    val authSuccess: LiveData<Boolean> = _authSuccess

    private val _authError = MutableLiveData<String?>()
    val authError: LiveData<String?> = _authError

    fun login(email: String, senha: String) {
        AuthManager.login(email, senha) { success, error ->
            _authSuccess.postValue(success)
            _authError.postValue(error)
        }
    }

    fun register(email: String, senha: String) {
        AuthManager.register(email, senha) { success, error ->
            _authSuccess.postValue(success)
            _authError.postValue(error)
        }
    }
}