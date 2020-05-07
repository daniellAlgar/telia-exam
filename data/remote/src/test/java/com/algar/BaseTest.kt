package com.algar

import com.algar.remote.DataSource
import com.algar.remote.di.remoteModule
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.koin.test.KoinTest
import java.io.File
import org.koin.core.KoinApplication
import org.koin.core.context.loadKoinModules
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.test.inject

abstract class BaseTest: KoinTest {

    protected val networkService: DataSource by inject()
    protected lateinit var mockServer: MockWebServer

    @Before
    open fun setUp() {
        configureMockServer()
        configureDI()
    }

    @After
    open fun tearDown() {
        stopMockServer()
        stopKoin()
    }

    private fun stopMockServer() {
        mockServer.shutdown()
    }

    private fun configureDI() {
        startKoin { KoinApplication.init() }
        loadKoinModules(listOf(
            remoteModule(
                baseUrl = mockServer.url("/").toString()
            )
        ))
    }

    private fun configureMockServer() {
        mockServer = MockWebServer()
        mockServer.start()
    }

    fun mockHttpResponse(
        server: MockWebServer,
        fileName: String,
        code: Int
    ) {
        server.enqueue(
            MockResponse()
                .setResponseCode(code)
                .setBody(getJson(path = fileName))
        )
    }

    @Suppress("RECEIVER_NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
    private fun getJson(path: String): String {
        val uri = javaClass.classLoader?.getResource(path)
        val file = File(uri?.path!!)
        return String(file.readBytes())
    }
}