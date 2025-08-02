package com.teste.exerciciolist.data.repository

import androidx.lifecycle.LiveData
import com.teste.exerciciolist.data.firebase.FirestoreManager
import com.teste.exerciciolist.data.local.dao.ExercicioDao
import com.teste.exerciciolist.data.local.entity.ExercicioEntity
import com.teste.exerciciolist.data.local.entity.TreinoEntity
import com.teste.exerciciolist.data.model.ModelByEntity.toEntity
import com.teste.exerciciolist.data.model.ModelByEntity.toModel
import javax.inject.Inject


class ExercicioRepository @Inject constructor(
    private val exercicioDao: ExercicioDao,
) {

    fun getLiveByTreinoId(treinoId: Int): LiveData<List<ExercicioEntity>> {
        return exercicioDao.getByTreino(treinoId)
    }

    suspend fun adicionarExercicio(
        exercicio: ExercicioEntity,
        userId: String,
        treinoId: String
    ): ExercicioEntity? {
        val remoteId = FirestoreManager.salvarExercicio(userId, treinoId, exercicio.toModel())

        if (remoteId.isEmpty()) return null

        // Salva localmente com o remoteId recebido
        val exercicioComId = exercicio.copy(remoteId = remoteId)
        exercicioDao.insert(exercicioComId)
        return exercicioComId
    }

    suspend fun atualizarExercicio(
        exercicio: ExercicioEntity,
        userId: String,
        treinoId: String
    ): Boolean {
        var success = false

        exercicio.remoteId?.let {
            success = FirestoreManager.atualizarExercicio(userId, treinoId, it, exercicio.toModel())
        }

        if (success) exercicioDao.update(exercicio)
        return success
    }

    suspend fun sincronizarExercicios(userId: String, rTreinoId: String, lTreinoId: Int) {
        val remotos = FirestoreManager.listarExercicios(userId, rTreinoId)

        val locais = remotos.map { (id, e) ->
            e.toEntity(id, lTreinoId)
        }

        exercicioDao.deleteByTreinoId(lTreinoId)
        locais.forEach { exercicioDao.insert(it) }
    }

    suspend fun deletarExercicio(
        exercicio: ExercicioEntity,
        userId: String,
        treinoId: String
    ): Boolean {
        var success = false

        exercicio.remoteId?.let {
            success = FirestoreManager.deletarExercicio(userId, treinoId, it)
        }

        if (success) exercicioDao.delete(exercicio)

        return success
    }
}