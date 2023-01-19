package com.example.potterguide.di.modulos

import com.example.potterguide.repositorio.LivroRepositorio
import com.example.potterguide.ui.activity.recyclerview.adapter.ListaLivrosAdapter
import com.example.potterguide.ui.viewModel.LivrosViewModel
import com.example.potterguide.webclient.RetrofitInicializadorLivros
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val livrosModulos = module {

    single {
        RetrofitInicializadorLivros().harrypotterServiceLivros
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

