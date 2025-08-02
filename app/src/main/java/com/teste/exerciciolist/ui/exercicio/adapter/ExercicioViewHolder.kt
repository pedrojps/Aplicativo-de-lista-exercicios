package com.teste.exerciciolist.ui.exercicio.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.teste.exerciciolist.R
import com.teste.exerciciolist.data.local.entity.ExercicioEntity
import com.teste.exerciciolist.data.local.entity.TreinoEntity
import com.teste.exerciciolist.data.model.Treino
import com.teste.exerciciolist.databinding.ItemExercicioBinding
import com.teste.exerciciolist.databinding.ItemTreinoBinding
import com.teste.exerciciolist.utils.UtilImage

class ExercicioViewHolder(val binding: ItemExercicioBinding): RecyclerView.ViewHolder(binding.root) {

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

        UtilImage.loadImage(itemView.context, binding.imageView2, item.imagemUrl)

    }
}