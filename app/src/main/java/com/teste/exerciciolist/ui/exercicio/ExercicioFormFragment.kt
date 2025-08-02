package com.teste.exerciciolist.ui.exercicio

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.teste.exerciciolist.data.firebase.AuthManager
import com.teste.exerciciolist.data.local.database.AppDatabase
import com.teste.exerciciolist.data.local.entity.ExercicioEntity
import com.teste.exerciciolist.data.local.entity.TreinoEntity
import com.teste.exerciciolist.data.repository.ExercicioRepository
import com.teste.exerciciolist.databinding.FragmentExercicioFromBinding
import com.teste.exerciciolist.viewmodel.ExercicioViewModel

class ExercicioFormFragment : Fragment() {

    private lateinit var binding: FragmentExercicioFromBinding
    private var treino: TreinoEntity? = null
    private var exercicio: ExercicioEntity? = null

    private val viewModel: ExercicioViewModel by lazy {
        val dao = AppDatabase.getInstance(requireContext()).exercicioDao()
        val repo = ExercicioRepository(dao)
        val factory = ExercicioViewModel.ExercicioViewModelFactory(repo)
        ViewModelProvider(this, factory)[ExercicioViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loadData()
    }

    private fun loadData(){
        treino = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU)  {
            arguments?.getSerializable(PARAM_LIST_F, TreinoEntity::class.java)
        } else {
            @Suppress("DEPRECATION")
            arguments?.getSerializable(PARAM_LIST_F) as? TreinoEntity?
        }

        exercicio = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU)  {
            arguments?.getSerializable(PARAM_EDIT_F, ExercicioEntity::class.java)
        } else {
            @Suppress("DEPRECATION")
            arguments?.getSerializable(PARAM_EDIT_F) as? ExercicioEntity?
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentExercicioFromBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.btnSalvar.setOnClickListener {
            val nome = binding.nome.text.toString().trim()
            val descricao = binding.descrition.text.toString().trim()

            if (nome.isEmpty()) {
                Toast.makeText(requireContext(), "Nome é obrigatório", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            AuthManager.getUserId()?.let {
                viewModel.criarExercicio(nome, descricao, it,"" , treino?.remoteId.toString())
            }
            parentFragmentManager.popBackStack()
        }
    }

    companion object{
        const val PARAM_LIST_F = "treino_list_f"
        const val PARAM_EDIT_F = "treino_edit_f"
    }
}
