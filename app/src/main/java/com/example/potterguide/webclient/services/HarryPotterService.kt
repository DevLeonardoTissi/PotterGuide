package com.example.potterguide.webclient.services

import com.example.potterguide.webclient.model.FeiticoResposta
import com.example.potterguide.webclient.model.PersonagemResposta
import retrofit2.http.GET

interface HarryPotterService {

    @GET("characters")
    suspend fun buscaTodos() : List<PersonagemResposta>

    @GET("characters/students")
    suspend fun buscaTodosAlunos() : List<PersonagemResposta>

    @GET("characters/staff")
    suspend fun buscaTodosFuncionarios() : List<PersonagemResposta>

    @GET("characters/house/gryffindor")
    suspend fun buscaTodosGrifinoria() : List<PersonagemResposta>

    @GET("characters/house/slytherin")
    suspend fun buscaTodosSonserina() : List<PersonagemResposta>


    @GET("characters/house/ravenclaw")
    suspend fun buscaTodosCorvinal() : List<PersonagemResposta>


    @GET("characters/house/hufflepuff")
    suspend fun buscaTodosLufaLufa() : List<PersonagemResposta>

    @GET("spells")
    suspend fun buscaFeitiços(): List<FeiticoResposta>


}

