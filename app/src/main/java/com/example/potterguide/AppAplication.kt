package com.example.potterguide

import android.app.Application
import com.example.potterguide.di.modulos.appModulos
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class AppApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@AppApplication)
            modules(appModulos)
        }
    }
}

