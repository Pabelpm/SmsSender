package com.pabloprieto.smssender.data

class PhoneNumbersRepository {
    suspend fun retrievePhoneNumbers(): List<PhoneNumber> {
        var result: PhoneNumbersResult
        result = try {
            PhoneNumbersClient.instance.getPhoneNumbers()
        } catch (e: Exception){
            PhoneNumbersResult(arrayListOf(PhoneNumberResult(prefix = "+ 34", number = "680 54 26 63")))

        }
        return result.phoneNumbers.map { it.toDomainModel() }
    }
}

private fun PhoneNumberResult.toDomainModel():PhoneNumber {
   return  PhoneNumber(prefix = this.prefix.filter{ !it.isWhitespace() }, number= this.number.filter{ !it.isWhitespace() })
}
