package com.teste.exerciciolist

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.teste.exerciciolist.data.local.entity.ExercicioEntity
import com.teste.exerciciolist.data.local.entity.TreinoEntity
import com.teste.exerciciolist.ui.exercicio.ExercicioFormFragment
import com.teste.exerciciolist.ui.exercicio.ExercicioFormFragment.Companion.PARAM_EDIT_F
import com.teste.exerciciolist.ui.exercicio.ExercicioFormFragment.Companion.PARAM_LIST_F
import com.teste.exerciciolist.ui.exercicio.ExercicioListFragment
import com.teste.exerciciolist.ui.exercicio.ExercicioListFragment.Companion.PARAM_LIST
import com.teste.exerciciolist.ui.exercicio.ExercicioShowFragment
import com.teste.exerciciolist.ui.exercicio.ExercicioShowFragment.Companion.PARAM_EDIT_S
import com.teste.exerciciolist.ui.exercicio.ExercicioShowFragment.Companion.PARAM_LIST_S
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


        showFragmento(fragment)
    }

    fun abrirListaDeExercicios(treino: TreinoEntity) {
        val fragment = ExercicioListFragment().apply {
            arguments = Bundle().apply {
                putSerializable(PARAM_LIST, treino)
            }
        }


        showFragmento(fragment)
    }

    fun abrirExercicios(treino: TreinoEntity, exercicio: ExercicioEntity? = null) {
        val fragment = ExercicioShowFragment().apply {
            arguments = Bundle().apply {
                putSerializable(PARAM_LIST_S, treino)
                exercicio?.let { putSerializable(PARAM_EDIT_S, it) }
            }
        }


        showFragmento(fragment)
    }

    fun abrirFormularioExercicio(treino: TreinoEntity, exercicio: ExercicioEntity? = null) {
        val fragment = ExercicioFormFragment().apply {
            arguments = Bundle().apply {
                putSerializable(PARAM_LIST_F, treino)
                exercicio?.let { putSerializable(PARAM_EDIT_F, it) }
            }
        }

        showFragmento(fragment)
    }

    fun showFragmento(fragment: androidx.fragment.app.Fragment) {

        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .addToBackStack(null)
            .commit()
    }
}