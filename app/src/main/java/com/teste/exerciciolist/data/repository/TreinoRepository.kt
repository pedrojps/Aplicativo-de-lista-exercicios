package com.teste.exerciciolist.data.repository

import androidx.lifecycle.LiveData
import com.teste.exerciciolist.data.firebase.FirestoreManager
import com.teste.exerciciolist.data.local.dao.ExercicioDao
import com.teste.exerciciolist.data.local.dao.TreinoDao
import com.teste.exerciciolist.data.local.entity.TreinoEntity
import com.teste.exerciciolist.data.model.ModelByEntity.toEntity
import com.teste.exerciciolist.data.model.ModelByEntity.toModel
import javax.inject.Inject

class TreinoRepository @Inject constructor(
    private val treinoDao: TreinoDao,
    private val exercicioDao: ExercicioDao
) {

    fun getTreinos(): LiveData<List<TreinoEntity>> {
        return treinoDao.getAll()
    }

    suspend fun adicionarTreino(treino: TreinoEntity, userId: String): Boolean {
        // 1. Salva no Firestore
        val remoteId = FirestoreManager.salvarTreino(userId, treino.toModel())

        if (remoteId.isEmpty()) return false

        // 2. Salva localmente
        treino.remoteId = remoteId
        treinoDao.insertR(treino)
        return true
    }

    suspend fun atualizarTreino(treino: TreinoEntity, userId: String):Boolean {
        var success = false
        // 1. Salva no Firestore
        treino.remoteId?.let {
            success = FirestoreManager.atualizarTreino(userId, it, treino.toModel())
        }

        // 2. Salva localmente
        if (success) treinoDao.update(treino)
        return success
    }

    suspend fun sincronizarTreinos(userId: String) {
        val remotos = FirestoreManager.listarTreinos(userId)
        remotos?:return
        
        // Converte para entidade local
        val locais = remotos.map { (id, t) ->
            t.toEntity(id)
        }

        treinoDao.deleteAll()
        locais.forEach {
            val idTreino = treinoDao.insert(it)
            val exerciciosR = FirestoreManager.listarExercicios(userId, it.remoteId.toString())
            exerciciosR?:return@forEach

            val exerciciosL = exerciciosR.map { (id, e) ->
                e.toEntity(id, idTreino.toInt())
            }

            try {
                exercicioDao.deleteByTreinoId(it.id)
                exercicioDao.insert(exerciciosL)
            }catch (e: Exception){
                e.printStackTrace()
            }
        }
    }

    suspend fun deletarTreino(treino: TreinoEntity, userId: String): Boolean {
        // 1. Deletar do Firestore
        var success = false
        treino.remoteId?.let {
            success = FirestoreManager.deletarTreino(userId, it)
        }

        // 2. Deletar do Room
        if (success) treinoDao.delete(treino)

        return success
    }
}
