package com.pabloprieto.smssender.data

import kotlinx.serialization.Serializable

@Serializable
data class PhoneNumbersResult(
    val results: List<PhoneNumberResult>
)

@Serializable
data class PhoneNumberResult(
    val prefix: String,
    val number: String
)