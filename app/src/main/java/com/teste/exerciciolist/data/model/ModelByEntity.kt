package com.teste.exerciciolist.data.model

import com.teste.exerciciolist.data.local.entity.TreinoEntity

object ModelByEntity {
    fun TreinoEntity.toModel(): Treino {
        return Treino(nome, descricao, data)
    }

    fun Treino.toEntity(remoteId: String? = null): TreinoEntity {
        return TreinoEntity(nome = nome, descricao = descricao, data = data, remoteId = remoteId)
    }
}