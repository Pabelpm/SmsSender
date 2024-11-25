package com.pabloprieto.smssender.data

import retrofit2.http.GET

interface PhoneNumberService {

    @GET("/prueba")
    suspend fun getPhoneNumbers(): PhoneNumbersResult
}