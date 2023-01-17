package com.example.potterguide.di.modulos

import com.example.potterguide.repositorio.LivroRepositorio
import com.example.potterguide.ui.activity.recyclerview.adapter.ListaLivrosAdapter
import com.example.potterguide.ui.viewModel.LivrosViewModel
import com.example.potterguide.webclient.RetrofitInicializador
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val livrosModulos = module {

    single {
        RetrofitInicializador().harryPotterService
    }

    single {
        LivroRepositorio(get())
    }

    single {
        ListaLivrosAdapter()
    }

    viewModel {
        LivrosViewModel(get())
    }

}

