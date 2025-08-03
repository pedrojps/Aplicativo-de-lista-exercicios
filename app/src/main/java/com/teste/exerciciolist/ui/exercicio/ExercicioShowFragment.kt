package com.teste.exerciciolist.ui.exercicio

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.teste.exerciciolist.MainActivity
import com.teste.exerciciolist.data.firebase.AuthManager
import com.teste.exerciciolist.data.local.database.AppDatabase
import com.teste.exerciciolist.data.local.entity.ExercicioEntity
import com.teste.exerciciolist.data.local.entity.TreinoEntity
import com.teste.exerciciolist.data.repository.ExercicioRepository
import com.teste.exerciciolist.databinding.FragmentExercicioShowBinding
import com.teste.exerciciolist.ui.exercicio.ExercicioFormFragment.Companion.PARAM_EDIT_F
import com.teste.exerciciolist.ui.exercicio.ExercicioFormFragment.Companion.PARAM_LIST_F
import com.teste.exerciciolist.utils.UtilImage
import com.teste.exerciciolist.viewmodel.ExercicioViewModel

class ExercicioShowFragment : Fragment() {

    private lateinit var binding: FragmentExercicioShowBinding
    private var exercicio: ExercicioEntity? = null
    private var treino: TreinoEntity? = null

    private val viewModel: ExercicioViewModel by lazy {
        val dao = AppDatabase.getInstance(requireContext()).exercicioDao()
        val repo = ExercicioRepository(dao)
        val factory = ExercicioViewModel.ExercicioViewModelFactory(repo)
        ViewModelProvider(this, factory)[ExercicioViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentExercicioShowBinding.inflate(inflater, container, false)

        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)

        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loadData()
    }

    private fun loadData(){
        treino = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU)  {
            arguments?.getSerializable(PARAM_LIST_S, TreinoEntity::class.java)
        } else {
            @Suppress("DEPRECATION")
            arguments?.getSerializable(PARAM_LIST_S) as? TreinoEntity?
        }

        exercicio = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU)  {
            arguments?.getSerializable(PARAM_EDIT_S, ExercicioEntity::class.java)
        } else {
            @Suppress("DEPRECATION")
            arguments?.getSerializable(PARAM_EDIT_S) as? ExercicioEntity?
        }

        treino?.let {
            viewModel.setTreinoId(it)
            viewModel.setExercicioId(exercicio?: return)
        }
    }

    private fun setData(){
        viewModel.exercicio.observe(viewLifecycleOwner) {
            binding.nomeTreino.text = (it.nome)
            binding.discritionTreino.text = (it.observacoes)
            UtilImage.loadImage(context,binding.imageView2, it.imagemUrl)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setListener()
        setData()
    }

    private fun setListener(){

        binding.btnEdite.setOnClickListener {
            treino?.let { treino ->
                (activity as MainActivity).abrirFormularioExercicio(treino, exercicio)
            }
        }

        binding.btnDelete.setOnClickListener {
            exercicio?.let {
                viewModel.deletarExercicio(it, AuthManager.getUserId() ?: return@let,treino?.remoteId.toString())
                viewModel.deletarExercicio(it, AuthManager.getUserId() ?: return@let, treino?.remoteId.toString())
                parentFragmentManager.popBackStack()
            }
        }
    }



    companion object{
        const val PARAM_LIST_S = "treino_list_S"
        const val PARAM_EDIT_S = "treino_edit_S"
    }
}