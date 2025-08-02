package com.teste.exerciciolist.ui.exercicio.adapter

import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.recyclerview.widget.RecyclerView
import com.teste.exerciciolist.data.local.entity.ExercicioEntity
import com.teste.exerciciolist.data.local.entity.TreinoEntity;
import com.teste.exerciciolist.databinding.ItemTreinoBinding;
import kotlin.Unit;

class ExercicioAdapter: RecyclerView.Adapter<ExercicioViewHolder>() {

    private var mItems: List<ExercicioEntity>? = null
    private var mListenerDeleter: ((ExercicioEntity) -> Unit)? = null
    private var mListenerUpdater: ((ExercicioEntity) -> Unit)? = null
    private var mListener: ((ExercicioEntity) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExercicioViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        return ExercicioViewHolder(ItemTreinoBinding.inflate(inflater, parent, false))
    }

    override fun getItemCount(): Int {
        return mItems?.size ?: 0
    }

    override fun onBindViewHolder(holder: ExercicioViewHolder, position: Int) {
        val item = mItems?.get(position) ?: return

        holder.onBind(item,{
            mListenerDeleter?.invoke(item)
        },{
            mListenerUpdater?.invoke(item)
        },{
            mListener?.invoke(item)
        })
    }

    fun submitList(list: List<ExercicioEntity>){
        mItems = list
        notifyDataSetChanged()
    }

    fun setListenerDeleter(listener: ((ExercicioEntity) -> Unit)?){
        mListenerDeleter = listener
    }

    fun setListenerUpdater(listener: ((ExercicioEntity) -> Unit)?){
        mListenerUpdater = listener
    }

    fun setListener(listener: ((ExercicioEntity) -> Unit)?){
        mListener = listener
    }
}