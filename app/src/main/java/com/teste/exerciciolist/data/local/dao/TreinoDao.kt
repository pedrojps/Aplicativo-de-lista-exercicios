package com.teste.exerciciolist.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.teste.exerciciolist.data.local.entity.TreinoEntity

@Dao
interface TreinoDao: BaseDao<TreinoEntity> {

    @Query("SELECT * FROM treinos")
    fun getAll(): LiveData<List<TreinoEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertR(treino: TreinoEntity)

    @Query("DELETE FROM treinos WHERE remoteId = :remoteId")
    fun deleteByRemoteId(remoteId: String)

    @Query("DELETE FROM treinos")
    fun deleteAll()

}