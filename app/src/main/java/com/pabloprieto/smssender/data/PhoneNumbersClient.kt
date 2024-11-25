package com.pabloprieto.smssender.data

import kotlinx.serialization.json.Json
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import retrofit2.create
import okhttp3.OkHttpClient

object MoviesClient {

    private val okHttpClient = OkHttpClient.Builder()
        .build()

    private val json = Json {
        ignoreUnknownKeys = true
    }

    val instance = Retrofit.Builder()
        .baseUrl("https://google.com")
        .client(okHttpClient)
        //.addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
        .build()
        .create<PhoneNumberService>()

}