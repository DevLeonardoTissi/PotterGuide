package com.example.potterguide.webclient

import com.example.potterguide.webclient.services.HarryPotterServiceLivros
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class RetrofitInicializadorLivros {

    private val retrofit = Retrofit.Builder()
        .baseUrl("https://www.googleapis.com/books/v1/")
        .addConverterFactory(MoshiConverterFactory.create())
        .build()

    val harrypotterServiceLivros = retrofit.create(HarryPotterServiceLivros::class.java)

}