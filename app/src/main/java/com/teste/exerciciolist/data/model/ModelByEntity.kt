package com.teste.exerciciolist.data.model

import com.teste.exerciciolist.data.local.entity.ExercicioEntity
import com.teste.exerciciolist.data.local.entity.TreinoEntity
import com.teste.exerciciolist.data.model.ModelByEntity.toModel

object ModelByEntity {
    fun TreinoEntity.toModel(): Treino {
        return Treino(nome, descricao, data)
    }

    fun Treino.toEntity(remoteId: String? = null): TreinoEntity {
        return TreinoEntity(nome = nome, descricao = descricao, data = data, remoteId = remoteId)
    }

    fun ExercicioEntity.toModel(): Exercicio = Exercicio(
        nome = this.nome,
        imagemUrl = this.imagemUrl,
        observacoes = this.observacoes
    )

    fun Exercicio.toEntity(remoteId: String, treinoId: Int): ExercicioEntity = ExercicioEntity(
        nome = this.nome,
        imagemUrl = this.imagemUrl,
        observacoes = this.observacoes,
        treinoId = treinoId,
        remoteId = remoteId
    )

}