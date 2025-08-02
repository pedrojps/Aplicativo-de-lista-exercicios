package com.teste.exerciciolist.data.local.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.teste.exerciciolist.data.local.dao.ExercicioDao
import com.teste.exerciciolist.data.local.dao.TreinoDao
import com.teste.exerciciolist.data.local.entity.ExercicioEntity
import com.teste.exerciciolist.data.local.entity.TreinoEntity

@Database(
    entities = [TreinoEntity::class, ExercicioEntity::class],
    version = 2
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun treinoDao(): TreinoDao
    abstract fun exercicioDao(): ExercicioDao

    companion object {
        @Volatile private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "treino_db"
                )
                    .fallbackToDestructiveMigration()
                    .build().also { INSTANCE = it }
            }
    }
}
