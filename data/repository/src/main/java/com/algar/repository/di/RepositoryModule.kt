package com.algar.repository.di

import com.algar.repository.AppDispatchers
import com.algar.repository.WeatherRepository
import com.algar.repository.WeatherRepositoryImpl
import kotlinx.coroutines.Dispatchers
import org.koin.dsl.module

val repositoryModule = module {
    single { AppDispatchers(main = Dispatchers.Main, io = Dispatchers.IO) }
    single<WeatherRepository> { WeatherRepositoryImpl(service = get()) }
}