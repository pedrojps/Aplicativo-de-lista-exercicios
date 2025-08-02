package com.teste.exerciciolist.viewmodel

import android.net.Uri
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import com.teste.exerciciolist.data.firebase.AuthManager
import com.teste.exerciciolist.data.firebase.StorageManager
import com.teste.exerciciolist.data.local.entity.ExercicioEntity
import com.teste.exerciciolist.data.local.entity.TreinoEntity
import com.teste.exerciciolist.data.repository.ExercicioRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.UUID

class ExercicioViewModel constructor(
    private val repository: ExercicioRepository
) : ViewModel() {

    private val _treinoId = MutableLiveData<TreinoEntity>()
    val exercicioId = MutableLiveData<ExercicioEntity>()

    val exercicios: LiveData<List<ExercicioEntity>> = _treinoId.switchMap { treinoId ->
        repository.getLiveByTreinoId(treinoId.id)
    }

    fun setTreinoId(treinoId: TreinoEntity) {
        _treinoId.value = treinoId
    }

    fun criarExercicio(nome: String, observacoes: String, userId: String) {
        var exercicio = ExercicioEntity(
            nome = nome,
            observacoes = observacoes,
            treinoId = _treinoId.value?.id ?: 0,
            imagemUrl = null
        )

        viewModelScope.launch(Dispatchers.IO) {
            exercicioId.postValue( repository.adicionarExercicio(exercicio, userId, _treinoId.value?.remoteId.toString()))
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

    fun uploadExercicioImage(uri: Uri?, exercicio: ExercicioEntity?) {
        if (uri != null) {
            val fileName = "exercicio_${exercicio?.remoteId}_${AuthManager.getUserId()}"

            StorageManager.uploadExercicioImage(
                uri = uri,
                fileName = fileName,
                onSuccess = { imageUrl ->
                    val exercicioParaSalvar = exercicio?.copy(imagemUrl = imageUrl)
                    atualizarExercicio(
                        exercicioParaSalvar?: return@uploadExercicioImage,
                        AuthManager.getUserId() ?: return@uploadExercicioImage,
                        _treinoId.value?.remoteId.toString())
                },
                onFailure = {
                    //Toast.makeText(context, "Erro ao enviar imagem", Toast.LENGTH_SHORT).show()
                }
            )
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