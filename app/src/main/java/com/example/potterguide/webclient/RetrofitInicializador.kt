package com.example.potterguide.webclient

import com.example.potterguide.webclient.services.HarryPotterService
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory


class RetrofitInicializador {

    private val retrofit = Retrofit.Builder()
        .baseUrl("https://hp-api.onrender.com/api/")
        .addConverterFactory(MoshiConverterFactory.create())
        .build()

    val harryPotterService = retrofit.create(HarryPotterService::class.java)
}