package com.teste.exerciciolist.ui.treino

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.teste.exerciciolist.MainActivity
import com.teste.exerciciolist.R
import com.teste.exerciciolist.data.firebase.AuthManager
import com.teste.exerciciolist.data.local.database.AppDatabase
import com.teste.exerciciolist.data.local.entity.TreinoEntity
import com.teste.exerciciolist.data.repository.TreinoRepository
import com.teste.exerciciolist.databinding.FragmentTreinoFormBinding
import com.teste.exerciciolist.viewmodel.TreinoViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TreinoFormFragment : Fragment() {

    private lateinit var binding: FragmentTreinoFormBinding
    private var treinoParaEditar: TreinoEntity? = null

    private val viewModel: TreinoViewModel by lazy {
        val treinoDao = AppDatabase.getInstance(requireContext()).treinoDao()
        val exercicioDao = AppDatabase.getInstance(requireContext()).exercicioDao()
        val repository = TreinoRepository(treinoDao, exercicioDao)
        val factory = TreinoViewModel.TreinoViewModelFactory(repository)
        ViewModelProvider(this, factory)[TreinoViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        treinoParaEditar = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU)  {
            arguments?.getSerializable(PARAM, TreinoEntity::class.java)
        } else {
            @Suppress("DEPRECATION")
            arguments?.getSerializable(PARAM) as? TreinoEntity?
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentTreinoFormBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        preencherCampos()
        setupListeners()
    }


    private fun preencherCampos() {
        treinoParaEditar?.let {
            binding.nome.setText(it.nome)
            binding.descrition.setText(it.descricao)

            binding.btnSalvar.text = getText(R.string.atualizar)
            binding.textTitulo.text = getText(R.string.atualizar_treino)
        } ?: run {
            binding.btnSalvar.text = getText(R.string.criar)
            binding.textTitulo.text = getText(R.string.criar_treino)
        }
    }

    private fun setupListeners() {
        binding.btnSalvar.setOnClickListener {
            val nome = binding.nome.text.toString()
            val descricao = binding.descrition.text.toString()

            if (nome.isBlank()){
                binding.nome.error = "Campo obrigatório"
                return@setOnClickListener
            }

            val userId = AuthManager.getUserId() ?: return@setOnClickListener


            if (treinoParaEditar != null) {
                val treinoEditado = treinoParaEditar!!.copy(
                    nome = nome,
                    descricao = descricao
                )
                viewModel.atualizarTreino(treinoEditado, userId)
            }else{
                viewModel.criarTreino(nome, descricao, userId)
            }

            (activity as? MainActivity)?.abrirListaDeTreinos()
        }
    }

    companion object{
        const val PARAM = "treino"
    }

}