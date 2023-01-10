package com.example.potterguide.di.modulos

import com.example.potterguide.repositorio.Repositorio
import com.example.potterguide.ui.viewModel.FeiticosViewModel
import com.example.potterguide.ui.viewModel.LivrosViewModel
import com.example.potterguide.ui.viewModel.PersonagensViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModulos = module {

    single {
        Repositorio(get())
    }

    viewModel {
        PersonagensViewModel(get())
    }

    viewModel {
        FeiticosViewModel(get())
    }

    viewModel {
        LivrosViewModel(get())
    }


}

