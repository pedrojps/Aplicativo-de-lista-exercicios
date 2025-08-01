package com.teste.exerciciolist.ui.treino.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.teste.exerciciolist.data.local.entity.TreinoEntity
import com.teste.exerciciolist.data.model.Treino
import com.teste.exerciciolist.databinding.ItemTreinoBinding

class TreinoViewHolder(val binding: ItemTreinoBinding): RecyclerView.ViewHolder(binding.root) {

    var mItem: TreinoEntity? = null

    fun onBind(item: TreinoEntity, listenerDeleter: View.OnClickListener, listenerUpdater: View.OnClickListener, listener: (TreinoEntity) -> Unit) {
        mItem = item
        binding.nomeTreino.text = item.nome
        binding.discritionTreino.text = item.descricao

        binding.btnEdite.setOnClickListener(listenerUpdater)
        binding.btnDelete.setOnClickListener(listenerDeleter)
        binding.root.setOnClickListener{
            listener(item)
        }

    }
}