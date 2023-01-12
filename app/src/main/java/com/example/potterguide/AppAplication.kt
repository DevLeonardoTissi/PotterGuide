package com.example.potterguide

import android.app.Application
import com.example.potterguide.di.modulos.feiticosModulos
import com.example.potterguide.di.modulos.livrosModulos
import com.example.potterguide.di.modulos.mainModulos
import com.example.potterguide.di.modulos.personagensModulos
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class AppApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@AppApplication)
            modules(personagensModulos, feiticosModulos, livrosModulos, mainModulos)
        }
    }
}

