package com.teste.exerciciolist.ui.treino

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.teste.exerciciolist.MainActivity
import com.teste.exerciciolist.R
import com.teste.exerciciolist.data.firebase.AuthManager
import com.teste.exerciciolist.data.local.database.AppDatabase
import com.teste.exerciciolist.data.repository.TreinoRepository
import com.teste.exerciciolist.databinding.FragmentTreinoListBinding
import com.teste.exerciciolist.ui.treino.adapter.TreinoAdapter
import com.teste.exerciciolist.viewmodel.TreinoViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TreinoListFragment : Fragment() {

    private lateinit var binding: FragmentTreinoListBinding

    private var adapter: TreinoAdapter = TreinoAdapter()

    private val viewModel: TreinoViewModel by lazy {
        val treinoDao = AppDatabase.getInstance(requireContext()).treinoDao()
        val exercicioDao = AppDatabase.getInstance(requireContext()).exercicioDao()
        val repository = TreinoRepository(treinoDao, exercicioDao)
        val factory = TreinoViewModel.TreinoViewModelFactory(repository)
        ViewModelProvider(this, factory)[TreinoViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentTreinoListBinding.inflate(layoutInflater)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val userId = AuthManager.getUserId() ?: return

        setOnClick()
        setAdapter()

        viewModel.sincronizar(userId)
    }

    private fun setOnClick(){
        binding.fabAddTreino.setOnClickListener {
            (activity as? MainActivity)?.abrirFormularioTreino()
        }
    }

    private fun setAdapter(){
        adapter.also { binding.recyclerView.adapter = it }
        binding.recyclerView.setLayoutManager(LinearLayoutManager(this.context))
        binding.recyclerView.setHasFixedSize(true)

        viewModel.treinos.observe(viewLifecycleOwner) { lista ->
            adapter.submitList(lista)
        }

        adapter.setListener {
            (activity as MainActivity).abrirListaDeExercicios(it)
        }
        adapter.setListenerDeleter {
            viewModel.deletarTreino(it, AuthManager.getUserId() ?: return@setListenerDeleter)
        }
        adapter.setListenerUpdater {
            (activity as? MainActivity)?.abrirFormularioTreino(it)
        }
    }
}