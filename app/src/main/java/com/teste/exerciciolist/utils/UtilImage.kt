package com.teste.exerciciolist.utils

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.teste.exerciciolist.R

object UtilImage {

    fun loadImage(context: Context?, imageView: ImageView, url: String?) {
        context?.let {
            Glide.with(it)
                .load(url)
                .placeholder(R.drawable.gym)
                .error(R.drawable.gym)
                .into(imageView)
        }


    }
}