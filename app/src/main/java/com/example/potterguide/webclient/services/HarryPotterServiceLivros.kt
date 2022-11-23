package com.example.potterguide.webclient.services

import com.example.potterguide.webclient.model.LivroResposta
import retrofit2.http.GET

interface HarryPotterServiceLivros {


    @GET("volumes?q=harry+potter")
    suspend fun buscaTodosLivros() : LivroResposta


}