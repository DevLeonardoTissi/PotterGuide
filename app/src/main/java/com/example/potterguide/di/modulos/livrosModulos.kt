package com.example.potterguide.di.modulos

import com.example.potterguide.ui.activity.recyclerview.adapter.ListaLivrosAdapter
import com.example.potterguide.ui.viewModel.LivrosViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val livrosModulos = module {

    single {
        ListaLivrosAdapter()
    }

    viewModel {
        LivrosViewModel(get())
    }


}

