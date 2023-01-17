package com.example.potterguide.di.modulos

import com.example.potterguide.repositorio.FeiticoRepositorio
import com.example.potterguide.ui.activity.recyclerview.adapter.ListaFeiticosAdapter
import com.example.potterguide.ui.viewModel.FeiticosViewModel
import com.example.potterguide.webclient.RetrofitInicializador
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

var feiticosModulos = module {

    single {
        RetrofitInicializador().harryPotterService
    }

    single {
        FeiticoRepositorio(get())
    }

    single {
        ListaFeiticosAdapter()
    }

    viewModel {
        FeiticosViewModel(get())
    }
}