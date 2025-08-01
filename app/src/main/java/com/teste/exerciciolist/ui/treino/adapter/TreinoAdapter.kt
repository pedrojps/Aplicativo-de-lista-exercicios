package com.teste.exerciciolist.ui.treino.adapter

import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.recyclerview.widget.RecyclerView
import com.teste.exerciciolist.data.local.entity.TreinoEntity;
import com.teste.exerciciolist.databinding.ItemTreinoBinding;
import kotlin.Unit;

class TreinoAdapter: RecyclerView.Adapter<TreinoViewHolder>() {

    private var mItems: List<TreinoEntity>? = null
    private var mListenerDeleter: ((TreinoEntity) -> Unit)? = null
    private var mListenerUpdater: ((TreinoEntity) -> Unit)? = null
    private var mListener: ((TreinoEntity) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TreinoViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        return TreinoViewHolder(ItemTreinoBinding.inflate(inflater, parent, false))
    }

    override fun getItemCount(): Int {
        return mItems?.size ?: 0
    }

    override fun onBindViewHolder(holder: TreinoViewHolder, position: Int) {
        val item = mItems?.get(position) ?: return

        holder.onBind(item,{
            mListenerDeleter?.invoke(item)
        },{
            mListenerUpdater?.invoke(item)
        },{
            mListener?.invoke(item)
        })
    }

    fun submitList(list: List<TreinoEntity>){
        mItems = list
        notifyDataSetChanged()
    }

    fun setListenerDeleter(listener: ((TreinoEntity) -> Unit)?){
        mListenerDeleter = listener
    }

    fun setListenerUpdater(listener: ((TreinoEntity) -> Unit)?){
        mListenerUpdater = listener
    }

    fun setListener(listener: ((TreinoEntity) -> Unit)?){
        mListener = listener
    }
}