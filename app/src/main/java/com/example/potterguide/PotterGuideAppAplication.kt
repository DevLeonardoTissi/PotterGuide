package com.example.potterguide

import android.app.Application
import com.example.potterguide.di.modulos.*
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class PotterGuideAppAplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@PotterGuideAppAplication)
            modules(mainModulos ,personagensModulos, feiticosModulos, livrosModulos)
        }
    }
}

