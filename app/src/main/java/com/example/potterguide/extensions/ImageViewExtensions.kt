package com.example.potterguide.extensions

import android.widget.ImageView
import coil.load
import com.example.potterguide.R

fun ImageView.tentaCarregarImagem(
    imagem: String? = null,
    fallback: Int = R.drawable.semfoto
){
    load(imagem) {
        fallback(fallback)
        error(R.color.Verde_principal)
        placeholder(R.color.Verde_principal)
    }
}