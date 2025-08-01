package com.teste.exerciciolist.data.firebase

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.teste.exerciciolist.data.model.Treino
import kotlinx.coroutines.tasks.await

object FirestoreManager {

    private const val USUARIOS_COLLECTION = "usuarios"
    private const val TREINOS_SUBCOLLECTION = "treinos"

    suspend fun salvarTreino(userId: String, treino: Treino):String {
        return try {
            val doc = FirebaseFirestore.getInstance()
                .collection(USUARIOS_COLLECTION)
                .document(userId)
                .collection(TREINOS_SUBCOLLECTION)
                .add(treino)
                .await()
            Log.d("FirestoreManager", "Treino salvo com sucesso.")
            doc.id
        } catch (e: Exception) {
            Log.e("FirestoreManager", "Erro ao salvar treino: ${e.message}")
            String()
        }
    }

    suspend fun atualizarTreino(userId: String, treinoId: String, treino: Treino):Boolean {
            return try {
                FirebaseFirestore.getInstance()
                    .collection(USUARIOS_COLLECTION)
                    .document(userId)
                    .collection(TREINOS_SUBCOLLECTION)
                    .document(treinoId)
                    .set(treino)
                    .await()
                Log.d("FirestoreManager", "Treino salvo com sucesso.")
                true
            } catch (e: Exception) {
                Log.e("FirestoreManager", "Erro ao salvar treino: ${e.message}")
                false
            }
    }

    suspend fun listarTreinos(userId: String): List<Pair<String, Treino>> {
        return try {
            val snapshot = FirebaseFirestore.getInstance()
                .collection(USUARIOS_COLLECTION)
                .document(userId)
                .collection(TREINOS_SUBCOLLECTION)
                .get()
                .await()

            snapshot.documents.mapNotNull { doc ->
                val treino = doc.toObject(Treino::class.java)
                treino?.let {
                    doc.id to it // Pair(documentId, treino)
                }
            }
        } catch (e: Exception) {
            Log.e("FirestoreManager", "Erro ao listar treinos: ${e.message}")
            emptyList()
        }
    }

    suspend fun deletarTreino(userId: String, treinoId: String): Boolean {
        return try {
            FirebaseFirestore.getInstance().collection(USUARIOS_COLLECTION)
                .document(userId)
                .collection(TREINOS_SUBCOLLECTION)
                .document(treinoId)
                .delete()
                .await()
            Log.d("FirestoreManager", "Treino deletado com sucesso.")
            true
        } catch (e: Exception) {
            Log.e("FirestoreManager", "Erro ao deletar treino: ${e.message}")
            false
        }
    }
}