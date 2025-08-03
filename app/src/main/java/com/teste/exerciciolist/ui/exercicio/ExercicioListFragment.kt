package com.teste.exerciciolist.ui.exercicio

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.teste.exerciciolist.MainActivity
import com.teste.exerciciolist.R
import com.teste.exerciciolist.data.firebase.AuthManager
import com.teste.exerciciolist.data.local.database.AppDatabase
import com.teste.exerciciolist.data.local.entity.TreinoEntity
import com.teste.exerciciolist.data.repository.ExercicioRepository
import com.teste.exerciciolist.databinding.FragmentExercicioListBinding
import com.teste.exerciciolist.ui.exercicio.adapter.ExercicioAdapter
import com.teste.exerciciolist.ui.treino.TreinoFormFragment.Companion.PARAM
import com.teste.exerciciolist.viewmodel.ExercicioViewModel

class ExercicioListFragment : Fragment() {

    private lateinit var binding: FragmentExercicioListBinding
    private var treino: TreinoEntity? = null

    private val viewModel: ExercicioViewModel by lazy {
        val dao = AppDatabase.getInstance(requireContext()).exercicioDao()
        val repo = ExercicioRepository(dao)
        val factory = ExercicioViewModel.ExercicioViewModelFactory(repo)
        ViewModelProvider(this, factory)[ExercicioViewModel::class.java]
    }

    private var adapter = ExercicioAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        treino = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU)  {
            arguments?.getSerializable(PARAM_LIST, TreinoEntity::class.java)
        } else {
            @Suppress("DEPRECATION")
            arguments?.getSerializable(PARAM_LIST) as? TreinoEntity?
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentExercicioListBinding.inflate(inflater, container, false)

        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setParametro()
        setAdapter()
        setOnClick()
    }

    private fun setParametro(){
        treino?.let {
            binding.nomeTreino.text = it.nome
            binding.discritionTreino.text = it.descricao

            viewModel.setTreinoId(it)
            AuthManager.getUserId()?.let { id ->
                viewModel.sincronizar(id, it)
            }
        }
    }

    private fun setOnClick(){
        binding.btnAdd.setOnClickListener {
            treino?.let { treino ->
                (activity as MainActivity).abrirFormularioExercicio(treino)
            }
        }
    }

    private fun setAdapter(){

        adapter.setListener {
            treino?.let { treino ->
                (activity as MainActivity).abrirExercicios(treino, it)
            }
        }
        adapter.setListenerDeleter {
            viewModel.deletarExercicio(it, AuthManager.getUserId() ?: return@setListenerDeleter, treino?.remoteId?:return@setListenerDeleter)
        }
        adapter.setListenerUpdater {
            treino?.let { treino ->
                (activity as MainActivity).abrirFormularioExercicio(treino, it)
            }
        }

        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())

        viewModel.exercicios.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }
    }

    companion object{
        const val PARAM_LIST = "treino_list"
    }
}
