package com.example.potterguide.di.modulos

import com.example.potterguide.ui.activity.recyclerview.adapter.ListaPersonagensAdapter
import com.example.potterguide.ui.viewModel.PersonagensViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val personagensModulos = module {

    single {
        ListaPersonagensAdapter()
    }

    viewModel{
        PersonagensViewModel(get())
    }


}