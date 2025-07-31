package com.teste.exerciciolist.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import com.teste.exerciciolist.data.local.entity.TreinoEntity

@Dao
interface TreinoDao: BaseDao<TreinoEntity> {

    @Query("SELECT * FROM treinos")
    fun getAll(): LiveData<List<TreinoEntity>>

}