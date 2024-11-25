package com.pabloprieto.smssender.data

class PhoneNumber(
    val prefix: String,
    val number: String
) {
}

val fakeNumber = PhoneNumber(prefix = "+34", number = "680542663")
