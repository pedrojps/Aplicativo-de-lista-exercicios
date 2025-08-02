package com.teste.exerciciolist.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.teste.exerciciolist.data.local.entity.TreinoEntity
import com.teste.exerciciolist.data.repository.TreinoRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TreinoViewModel (
    private val repository: TreinoRepository
) : ViewModel() {

    val treinos: LiveData<List<TreinoEntity>> = repository.getTreinos()

    fun criarTreino(nome: String, descricao: String, userId: String) {
        val treino = TreinoEntity(nome = nome, descricao = descricao)
        viewModelScope.launch(Dispatchers.IO) {
            repository.adicionarTreino(treino, userId)
        }
    }

    fun atualizarTreino(treino: TreinoEntity, userId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.atualizarTreino(treino, userId)
        }
    }

    fun sincronizar(userId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.sincronizarTreinos(userId)
        }
    }

    fun deletarTreino(treino: TreinoEntity, userId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deletarTreino(treino, userId)
        }
    }

    class TreinoViewModelFactory(
        private val repository: TreinoRepository
    ) : ViewModelProvider.Factory {

        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(TreinoViewModel::class.java)) {
                return TreinoViewModel(repository) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }

}