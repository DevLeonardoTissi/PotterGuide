package com.example.potterguide.di.modulos

import com.example.potterguide.repositorio.PersonagemRepositorio
import com.example.potterguide.ui.fragment.recyclerview.adapter.ListaPersonagensAdapter
import com.example.potterguide.ui.viewModel.PersonagensViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val personagensModulos = module {


    single {
        PersonagemRepositorio(get())
    }

    single {
        ListaPersonagensAdapter()
    }

    viewModel {
        PersonagensViewModel(get())
    }

}