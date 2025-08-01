package com.teste.exerciciolist.data.model

import com.teste.exerciciolist.data.local.entity.TreinoEntity


data class Treino(
    val nome: String = "",
    val descricao: String = "",
    val data: Long = System.currentTimeMillis()
)


