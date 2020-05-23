package com.k0d4black.theforce.di

import com.k0d4black.theforce.data.api.StarWarsApiService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

class FakeStarWarsApiModule {

    val fakeNetworkModule = module {

        single { provideService(get()) }

        single {
            provideRetrofit(
                get(),
                provideBaseUrl()
            )
        }

        single { provideOkHttpClient() }
    }

    private fun provideOkHttpClient(): OkHttpClient {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        return OkHttpClient.Builder()
            .connectTimeout(60L, TimeUnit.SECONDS)
            .readTimeout(60L, TimeUnit.SECONDS)
            .addInterceptor(httpLoggingInterceptor).build()
    }

    private fun provideRetrofit(okHttpClient: OkHttpClient, url: String): Retrofit {
        return Retrofit.Builder()
            .baseUrl(url)
            .client(okHttpClient)
            .addConverterFactory(MoshiConverterFactory.create()).build()
    }

    private fun provideService(retrofit: Retrofit): StarWarsApiService =
        retrofit.create(StarWarsApiService::class.java)

    fun provideBaseUrl(): String = "http://localhost:8080/"
}