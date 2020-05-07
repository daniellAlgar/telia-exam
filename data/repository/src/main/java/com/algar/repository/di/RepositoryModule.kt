package com.algar.repository.di

import com.algar.repository.WeatherRepository
import com.algar.repository.WeatherRepositoryImpl
import org.koin.dsl.module

val repositoryModule = module {
    single<WeatherRepository> { WeatherRepositoryImpl(service = get()) }
}