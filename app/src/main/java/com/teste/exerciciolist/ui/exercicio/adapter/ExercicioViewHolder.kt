package com.teste.exerciciolist.ui.exercicio.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.teste.exerciciolist.data.local.entity.ExercicioEntity
import com.teste.exerciciolist.data.local.entity.TreinoEntity
import com.teste.exerciciolist.data.model.Treino
import com.teste.exerciciolist.databinding.ItemTreinoBinding

class ExercicioViewHolder(val binding: ItemTreinoBinding): RecyclerView.ViewHolder(binding.root) {

    var mItem: ExercicioEntity? = null

    fun onBind(item: ExercicioEntity, listenerDeleter: View.OnClickListener, listenerUpdater: View.OnClickListener, listener: (ExercicioEntity) -> Unit) {
        mItem = item
        binding.nomeTreino.text = item.nome
        binding.discritionTreino.text = item.observacoes

        binding.btnEdite.setOnClickListener(listenerUpdater)
        binding.btnDelete.setOnClickListener(listenerDeleter)
        binding.root.setOnClickListener{
            listener(item)
        }

    }
}