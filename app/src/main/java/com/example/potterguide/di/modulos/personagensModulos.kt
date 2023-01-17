package com.example.potterguide.di.modulos

import com.example.potterguide.repositorio.PersonagemRepositorio
import com.example.potterguide.ui.activity.recyclerview.adapter.ListaPersonagensAdapter
import com.example.potterguide.ui.viewModel.PersonagensViewModel
import com.example.potterguide.webclient.RetrofitInicializador
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val personagensModulos = module {

    single {
        RetrofitInicializador().harryPotterService
    }

    single {
        PersonagemRepositorio(get(), get())
    }

    single {
        ListaPersonagensAdapter()
    }

    viewModel {
        PersonagensViewModel(get())
    }

}