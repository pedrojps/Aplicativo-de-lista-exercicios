package com.teste.exerciciolist.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "treinos")
data class TreinoEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val nome: String,
    val descricao: String,
    val data: Long = System.currentTimeMillis()
)
