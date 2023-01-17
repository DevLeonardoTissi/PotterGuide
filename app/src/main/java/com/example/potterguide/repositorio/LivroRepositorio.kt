package com.example.potterguide.repositorio

import com.example.potterguide.model.Livro
import com.example.potterguide.webclient.services.HarryPotterServiceLivros

class LivroRepositorio(private val harrypotterservicelivros: HarryPotterServiceLivros) {

    suspend fun buscaLivros(): List<Livro> {
        val listaResposta = harrypotterservicelivros.buscaTodosLivros().items
        val listaLivro = listaResposta.map { livrosresposta ->
            livrosresposta.livro
        }
        return listaLivro
    }
}