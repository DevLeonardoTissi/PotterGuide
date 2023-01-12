package com.example.potterguide.di.modulos

import com.example.potterguide.repositorio.Repositorio
import org.koin.dsl.module

var mainModulos = module {

    single {
        Repositorio(get())
    }
}