package com.teste.exerciciolist.data.firebase

import com.google.firebase.auth.FirebaseAuth

object AuthManager {
    private val auth = FirebaseAuth.getInstance()

    fun login(email: String, senha: String, callback: (Boolean, String?) -> Unit) {
        auth.signInWithEmailAndPassword(email, senha)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    callback(true, null)
                } else {
                    callback(false, it.exception?.message)
                }
            }
    }

    fun register(email: String, senha: String, callback: (Boolean, String?) -> Unit) {
        auth.createUserWithEmailAndPassword(email, senha)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    callback(true, null)
                } else {
                    callback(false, it.exception?.message)
                }
            }
    }

    fun logout() {
        auth.signOut()
    }

    fun isLoggedIn(): Boolean {
        return auth.currentUser != null
    }

    fun getUserId(): String? {
        return auth.currentUser?.uid
    }
}
