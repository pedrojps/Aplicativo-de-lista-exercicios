package com.teste.exerciciolist.data.local.dao

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Update

interface BaseDao<T> {

    @Update
    fun update(entity: T): Int

    @Insert
    fun insert(entity: T): Long

    @Delete
    fun delete(entity: T)

    @Insert
    fun insert(entity: List<T>): LongArray

    @Update
    fun update(entity: List<T>): Int

    @Delete
    fun deleteAll(entity: List<T>)

}