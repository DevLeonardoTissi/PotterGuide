package com.example.potterguide.repositorio

import com.example.potterguide.model.Feitico
import com.example.potterguide.webclient.services.HarryPotterService

class FeiticoRepositorio(private val harrypotterservice: HarryPotterService) {


    suspend fun buscaFeiticos(): List<Feitico> {
        val listaFeiticoResposta = harrypotterservice.buscaFeitiços()
        val listaFeitico = listaFeiticoResposta.map { feiticoResposta ->
            feiticoResposta.feitico
        }
        return listaFeitico
    }
}