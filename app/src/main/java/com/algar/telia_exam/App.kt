package com.algar.telia_exam

import android.app.Application
import com.algar.telia_exam.di.appComponent
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.core.module.Module

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        configureDI()
    }

    private fun configureDI() = startKoin {
        androidContext(this@App)
        modules(provideComponents())
    }

    private fun provideComponents(): List<Module> = appComponent()
}