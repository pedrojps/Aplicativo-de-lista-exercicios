package com.teste.exerciciolist.utils

import android.content.Context
import android.content.Intent
import com.teste.exerciciolist.MainActivity
import com.teste.exerciciolist.ui.auth.LoginActivity
import com.teste.exerciciolist.ui.auth.RegisterActivity

object ScreenManager {

    fun toGoLogin(context:Context?){
        val intent = Intent(context, LoginActivity::class.java)
        context?.startActivity(intent)
    }

    fun toGoDetailView(context:Context?) {
        val intent = Intent(context, RegisterActivity::class.java)
        context?.startActivity(intent)
    }

    fun toGoMain(context:Context?) {
        val intent = Intent(context, MainActivity::class.java)
        context?.startActivity(intent)
    }


}
