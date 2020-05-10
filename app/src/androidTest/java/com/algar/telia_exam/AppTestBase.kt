package com.algar.telia_exam

import androidx.test.rule.ActivityTestRule
import com.algar.details.DetailsViewModel
import com.algar.home.HomeViewModel
import com.algar.repository.AppDispatchers
import com.algar.repository.WeatherRepository
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.koin.core.context.loadKoinModules
import org.koin.core.context.unloadKoinModules
import org.koin.dsl.module
import org.koin.test.KoinTest

open class AppTestBase : KoinTest {

    @Rule
    @JvmField
    val mainActivityTestRule = ActivityTestRule(MainActivity::class.java, true, false)

    @Before
    fun setup() {
        loadKoinModules(modules)
    }

    @After
    fun tearDown() {
        unloadKoinModules(modules)
    }

    val repositoryMock = mockk<WeatherRepository>(relaxed = true)
    private val repositoryModule = module {
        single { repositoryMock }
    }

    private val featureHomeModule = module {
        factory { HomeViewModel(repository = get(), dispatchers = get()) }
    }

    private val featureDetailsModule = module {
        factory { DetailsViewModel(repository = get(), dispatchers = get()) }
    }

    private val appDispatchersModule = module {
        factory { AppDispatchers(main = Dispatchers.Main, io = Dispatchers.IO) }
    }

    private val modules = listOf(
        repositoryModule,
        appDispatchersModule,
        featureHomeModule,
        featureDetailsModule
    )
}