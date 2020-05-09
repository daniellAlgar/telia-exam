package com.algar.remote.di

import com.algar.remote.NetworkService
import com.algar.remote.DataSource
import com.algar.remote.di.NamedInterceptor.*
import com.algar.remote.helpers.DateTimeSerializer
import com.algar.remote.helpers.Logger
import com.algar.remote.helpers.RetrofitCurlPrinterInterceptor
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import kotlinx.coroutines.Dispatchers
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.joda.time.DateTime
import org.koin.core.qualifier.Qualifier
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Provides the modules needed for our network layer to work.
 *
 * @param baseUrl The base api endpoint
 */
fun remoteModule(baseUrl: String) = module {

    single {
        Retrofit.Builder()
            .client(get())
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create(get()))
            .build()
    }

    factory {
        OkHttpClient.Builder()
            .addInterceptor(get<Interceptor>(namedInterceptor(HTTP_LOGGING_INTERCEPTOR)))
            .addInterceptor(get<Interceptor>(namedInterceptor(ADD_HEADERS_INTERCEPTOR)))
            .addInterceptor(get<Interceptor>(namedInterceptor(CURL_PRINTER_INTERCEPTOR)))
            .build()
    }

    factory<Interceptor>(namedInterceptor(type = CURL_PRINTER_INTERCEPTOR)) {
        RetrofitCurlPrinterInterceptor(logger = Logger)
    }

    factory<Interceptor>(namedInterceptor(type = HTTP_LOGGING_INTERCEPTOR)) {
        HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.HEADERS
        }
    }

    factory(namedInterceptor(type = ADD_HEADERS_INTERCEPTOR)) {
        Interceptor {
            val interceptor = it.request()
                .newBuilder()
                .addHeader("Accept", "application/json")
                .build()

            it.proceed(interceptor)
        }
    }


    single<Gson>{
        GsonBuilder()
            .registerTypeAdapter(DateTime::class.java, DateTimeSerializer.INSTANCE)
            .create()
    }

    factory { get<Retrofit>().create(NetworkService::class.java) }

    factory { DataSource(service = get(), dispatcher = get()) }

    factory { Dispatchers.IO }
}

private enum class NamedInterceptor {
    HTTP_LOGGING_INTERCEPTOR,
    ADD_HEADERS_INTERCEPTOR,
    CURL_PRINTER_INTERCEPTOR
}

private fun namedInterceptor(type: NamedInterceptor): Qualifier = named(type.toString())