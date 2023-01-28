package com.example.potterguide.di.modulos

import com.example.potterguide.webclient.RetrofitInicializador
import org.koin.dsl.module

val mainModulos = module {
    single {
        RetrofitInicializador().harryPotterService
    }

}
