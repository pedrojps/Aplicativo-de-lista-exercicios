package com.teste.exerciciolist.ui.exercicio

import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.teste.exerciciolist.data.firebase.AuthManager
import com.teste.exerciciolist.data.firebase.StorageManager
import com.teste.exerciciolist.data.local.database.AppDatabase
import com.teste.exerciciolist.data.local.entity.ExercicioEntity
import com.teste.exerciciolist.data.local.entity.TreinoEntity
import com.teste.exerciciolist.data.repository.ExercicioRepository
import com.teste.exerciciolist.databinding.FragmentExercicioFromBinding
import com.teste.exerciciolist.utils.UtilImage
import com.teste.exerciciolist.viewmodel.ExercicioViewModel

class ExercicioFormFragment : Fragment() {

    private lateinit var binding: FragmentExercicioFromBinding
    private var treino: TreinoEntity? = null
    private var exercicio: ExercicioEntity? = null
    private var selectedImageUri: Uri? = null

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

        treino?.let {
            viewModel.setTreinoId(it)
        }

    }

    private fun setData(){
        exercicio?.let {
            binding.nome.setText(it.nome)
            binding.descrition.setText(it.observacoes)
            UtilImage.loadImage(context,binding.imageView3, it.imagemUrl)
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
        setListener()
        setData()
    }

    private fun setListener(){
        binding.imageView3.setOnClickListener {
            imagePicker.launch("image/*")
        }
        binding.btnSalvar.setOnClickListener {
            salvarExercicio()
        }

        viewModel.exercicioId.observe(viewLifecycleOwner) {
            salvarImage(it)
        }
    }

    private fun salvarImage(remoteID: ExercicioEntity?) {
        viewModel.uploadExercicioImage(selectedImageUri, remoteID, exercicio == null)
        parentFragmentManager.popBackStack()
    }

    private val imagePicker = registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        selectedImageUri = uri
        binding.imageView3.setImageURI(uri)
    }


    private fun salvarExercicio() {
        val nome = binding.nome.text.toString().trim()
        val descricao = binding.descrition.text.toString().trim()

        if (nome.isEmpty()) {
            Toast.makeText(requireContext(), "Nome é obrigatório", Toast.LENGTH_SHORT).show()
            return
        }

        AuthManager.getUserId()?.let {
            if (exercicio == null)
                viewModel.criarExercicio(nome, descricao, it)
            else
                exercicio?.let { ex ->
                    viewModel.atualizarExercicio(ex.copy(nome = nome, observacoes = descricao), it, treino?.remoteId.toString())
                }
        }
    }

    companion object{
        const val PARAM_LIST_F = "treino_list_f"
        const val PARAM_EDIT_F = "treino_edit_f"
    }
}
