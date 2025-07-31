package com.teste.exerciciolist.data.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "exercicios",
    foreignKeys = [ForeignKey(
        entity = TreinoEntity::class,
        parentColumns = ["id"],
        childColumns = ["treinoId"],
        onDelete = ForeignKey.CASCADE
    )],
    indices = [Index(value = ["treinoId"])]
)
data class ExercicioEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val nome: String,
    val imagemUrl: String?,
    val observacoes: String,
    val treinoId: Int
)
