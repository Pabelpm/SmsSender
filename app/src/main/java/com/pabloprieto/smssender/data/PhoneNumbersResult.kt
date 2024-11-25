package com.pabloprieto.smssender.data

import kotlinx.serialization.Serializable

@Serializable
data class PhoneNumbersResult(
    val phoneNumbers: List<PhoneNumberResult>
)

@Serializable
data class PhoneNumberResult(
    val prefix: String,
    val number: String
)