package com.algar.local

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider.getApplicationContext
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.algar.local.dao.ForecastDao
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.runner.RunWith
import org.koin.core.KoinApplication
import org.koin.core.context.loadKoinModules
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.koin.test.inject

@RunWith(AndroidJUnit4::class)
abstract class BaseTest : KoinTest {

    val forecastDao: ForecastDao by inject()

    @Rule
    @JvmField
    val rule = InstantTaskExecutorRule()

    @Before
    fun setup() {
        startKoin { KoinApplication.init() }
        configureDI()
    }

    @After
    fun tearDown() {
        stopKoin()
    }

    private fun configureDI() {
        loadKoinModules(configureLocalModules(context = getApplicationContext()))
    }

    private fun configureLocalModules(context: Context) = module {
        single {
            Room.inMemoryDatabaseBuilder(context, ForecastDatabase::class.java)
                .allowMainThreadQueries()
                .build()
        }

        factory { get<ForecastDatabase>().forecastDao() }
    }
}