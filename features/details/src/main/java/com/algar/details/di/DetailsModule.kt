package com.algar.details.di

import com.algar.details.DetailsViewModel
import org.koin.dsl.module

val detailsModule = module {
    factory { DetailsViewModel(repository = get(), dispatchers = get()) }
}