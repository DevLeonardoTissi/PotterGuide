package com.example.potterguide.webclient.model

import com.example.potterguide.model.Livro
import com.example.potterguide.model.VolumeInfo

class ItemLivroResposta(
    private val volumeInfo: VolumeInfo

) {
    val livro: Livro
        get() = Livro(
            titulo = volumeInfo.title?.lowercase()?.capitalize() ?: "",
            subtitulo = volumeInfo.subtitle ?: "",
            autores = volumeInfo.authors ?: emptyList(),
            editora = volumeInfo.publisher ?: "",
            dataDePublicacao = volumeInfo.publishedDate ?: "",
            descricao = volumeInfo.description ?: "",
            numeroDePaginas = volumeInfo.pageCount ?: 0,
            categorias = volumeInfo.categories,
            imageLinks = volumeInfo.imageLinks,
            idioma = volumeInfo.language ?: ""

        )

}