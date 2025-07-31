package com.teste.exerciciolist.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import com.teste.exerciciolist.data.local.entity.ExercicioEntity

@Dao
interface ExercicioDao: BaseDao<ExercicioEntity> {

    @Query("SELECT * FROM exercicios WHERE treinoId = :treinoId")
    fun getByTreino(treinoId: Int): LiveData<List<ExercicioEntity>>

}
