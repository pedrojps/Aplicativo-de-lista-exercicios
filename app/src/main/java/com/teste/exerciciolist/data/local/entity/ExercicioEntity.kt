package com.teste.exerciciolist.data.local.entity

import android.os.Parcel
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import java.io.Serializable

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
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val nome: String,
    var imagemUrl: String?,
    val observacoes: String,
    val treinoId: Int,
    var remoteId: String? = null
): Serializable