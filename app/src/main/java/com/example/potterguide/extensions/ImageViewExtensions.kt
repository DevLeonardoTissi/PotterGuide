package com.example.potterguide.extensions

import android.widget.ImageView
import coil.load
import com.example.potterguide.R

fun ImageView.tentaCarregarImagem(
    imagem: String? = null,
    fallback: Int = R.color.Marrom_principal
){
    load(imagem) {
        fallback(fallback)
        error(R.color.Marrom_principal)
        placeholder(R.color.Marrom_principal)
    }
}