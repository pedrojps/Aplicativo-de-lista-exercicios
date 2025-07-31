package com.teste.exerciciolist.data.local.database

import android.content.Context
import androidx.room.Room
import com.teste.exerciciolist.data.local.dao.ExercicioDao
import com.teste.exerciciolist.data.local.dao.TreinoDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "treino_db"
        ).build()
    }

    @Provides fun provideTreinoDao(db: AppDatabase): TreinoDao = db.treinoDao()
    @Provides fun provideExercicioDao(db: AppDatabase): ExercicioDao = db.exercicioDao()
}