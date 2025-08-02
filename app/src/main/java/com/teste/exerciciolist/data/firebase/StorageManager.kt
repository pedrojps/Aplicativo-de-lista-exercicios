package com.teste.exerciciolist.data.firebase

import android.net.Uri
import com.google.firebase.storage.FirebaseStorage

object StorageManager {

    private val storage = FirebaseStorage.getInstance().reference

    fun uploadExercicioImage(
        uri: Uri,
        fileName: String,
        onSuccess: (String) -> Unit,
        onFailure: (Exception) -> Unit
    ) {
        val ref = storage.child("exercicios/$fileName.jpg")
        ref.putFile(uri)
            .continueWithTask { task ->
                if (!task.isSuccessful) {
                    throw task.exception ?: Exception("Upload failed")
                }
                ref.downloadUrl
            }
            .addOnSuccessListener { downloadUrl ->
                onSuccess(downloadUrl.toString())
            }
            .addOnFailureListener { e ->
                onFailure(e)
            }
    }
}
