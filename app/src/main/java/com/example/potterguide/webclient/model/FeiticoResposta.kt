package com.example.potterguide.webclient.model

import com.example.potterguide.model.Feitico

class FeiticoResposta(
    val name: String?,
    val description: String?
) {


    val feitico: Feitico
        get() = Feitico(
            nome = name ?: "",
            descricao = description ?: ""
        )
}