package com.algar.telia_exam.di

import com.algar.home.di.homeModule
import com.algar.local.di.localModule
import com.algar.model.Secrets.baseUrl
import com.algar.remote.di.remoteModule
import com.algar.repository.di.repositoryModule

fun appComponent() = listOf(
    remoteModule(baseUrl = baseUrl),
    repositoryModule,
    localModule,
    homeModule
)