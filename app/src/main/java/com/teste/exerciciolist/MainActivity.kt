package com.teste.exerciciolist

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.teste.exerciciolist.data.local.entity.TreinoEntity
import com.teste.exerciciolist.ui.treino.TreinoFormFragment
import com.teste.exerciciolist.ui.treino.TreinoListFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Abre o fragmento de lista na inicialização
        if (savedInstanceState == null) {
            abrirListaDeTreinos()
        }
    }

    fun abrirListaDeTreinos() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, TreinoListFragment())
            .commit()
    }

    fun abrirFormularioTreino(treino: TreinoEntity? = null) {
        val fragment = TreinoFormFragment()

        treino?.let {
            val bundle = Bundle().apply {
                putSerializable("treino", it)
            }
            fragment.arguments = bundle
        }

        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .addToBackStack(null)
            .commit()
    }
}