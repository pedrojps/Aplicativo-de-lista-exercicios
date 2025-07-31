package com.teste.exerciciolist.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.teste.exerciciolist.data.local.dao.ExercicioDao
import com.teste.exerciciolist.data.local.dao.TreinoDao
import com.teste.exerciciolist.data.local.entity.ExercicioEntity
import com.teste.exerciciolist.data.local.entity.TreinoEntity

@Database(
    entities = [TreinoEntity::class, ExercicioEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun treinoDao(): TreinoDao
    abstract fun exercicioDao(): ExercicioDao
}
