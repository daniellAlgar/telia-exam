package com.algar.local.di

import com.algar.local.ForecastDatabase
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val localModule = module {
    single { ForecastDatabase.buildDatabase(context = androidContext()) }
    factory { get<ForecastDatabase>().forecastDao() }
}