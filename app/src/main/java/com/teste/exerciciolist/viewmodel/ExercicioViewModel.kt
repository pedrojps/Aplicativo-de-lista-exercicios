package com.teste.exerciciolist.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import com.teste.exerciciolist.data.local.entity.ExercicioEntity
import com.teste.exerciciolist.data.local.entity.TreinoEntity
import com.teste.exerciciolist.data.repository.ExercicioRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ExercicioViewModel constructor(
    private val repository: ExercicioRepository
) : ViewModel() {

    private val _treinoId = MutableLiveData<TreinoEntity>()

    val exercicios: LiveData<List<ExercicioEntity>> = _treinoId.switchMap { treinoId ->
        repository.getLiveByTreinoId(treinoId.id)
    }

    fun setTreinoId(treinoId: TreinoEntity) {
        _treinoId.value = treinoId
    }

    fun criarExercicio(nome: String, observacoes: String, userId: String, imagemUrl: String ,treinoId: String) {
        val exercicio = ExercicioEntity(
            nome = nome,
            observacoes = observacoes,
            treinoId = _treinoId.value?.id ?: 0,
            imagemUrl = imagemUrl
        )

        viewModelScope.launch(Dispatchers.IO) {
            repository.adicionarExercicio(exercicio, userId, treinoId)
        }
    }

    fun atualizarExercicio(exercicio: ExercicioEntity, userId: String, treinoId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.atualizarExercicio(exercicio, userId, treinoId)
        }
    }

    fun sincronizar(userId: String, treino: TreinoEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            treino.remoteId?.let {
                repository.sincronizarExercicios(userId, it , treino.id)
            }
        }
    }

    fun deletarExercicio(exercicio: ExercicioEntity, userId: String, treinoId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deletarExercicio(exercicio, userId, treinoId)
        }
    }

    class ExercicioViewModelFactory(
        private val repository: ExercicioRepository
    ) : ViewModelProvider.Factory {

        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(ExercicioViewModel::class.java)) {
                return ExercicioViewModel(repository) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }

}