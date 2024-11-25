package com.pabloprieto.smssender.data

import retrofit2.http.GET

interface PhoneNumberService {

    @GET("")
    suspend fun getPhoneNumbers(): PhoneNumbersResult
}