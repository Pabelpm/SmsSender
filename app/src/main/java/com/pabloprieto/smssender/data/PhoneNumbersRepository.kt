package com.pabloprieto.smssender.data

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

class PhoneNumbersRepository {
    suspend fun retrievePhoneNumbers(): List<PhoneNumber> = withContext(Dispatchers.IO){
        delay(1000)
        arrayListOf(fakeNumber)
    }
}