package com.algar.home.di

import com.algar.home.HomeViewModel
import org.koin.dsl.module

val homeModule = module {
    factory { HomeViewModel(repository = get(), dispatchers = get())}
}