package com.pabloprieto.smssender.ui.home

import android.content.Context
import android.telephony.SmsManager
import android.widget.Toast
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pabloprieto.smssender.data.PhoneNumber
import com.pabloprieto.smssender.data.PhoneNumbersRepository
import com.pabloprieto.smssender.data.fakeNumber
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {

    fun getStringFromEnum(enum: SMSPermission):String{
        return when (enum){
            SMSPermission.PERMISSION_GRANTED -> "Permission granted"
            SMSPermission.PERMISSION_DENIED -> "Permission denied"
            SMSPermission.PERMISSION_NOT_REQUESTED -> "Permission not requested"
        }
    }

    var state by mutableStateOf(UiState())
        private set

    private val phoneNumbersRepository = PhoneNumbersRepository()

    fun clickRetrieveNumbers() {
        viewModelScope.launch {
            state = UiState(loading = true)
            delay(1000)
            state = UiState(loading = false, phoneNumbers = phoneNumbersRepository.retrievePhoneNumbers())
        }
    }

    fun sendSMS(context: Context, phoneNumber: String?, msg: String?) {
        try {
            val smsManager: SmsManager = SmsManager.getDefault()
            smsManager.sendTextMessage(phoneNumber, null, msg, null, null)
            Toast.makeText(
                context, "Message: $msg Sent to $phoneNumber",
                Toast.LENGTH_LONG
            ).show()
        } catch (ex: Exception) {
            Toast.makeText(
                context, ex.message.toString(),
                Toast.LENGTH_LONG
            ).show()
            ex.printStackTrace()
        }
    }

    data class UiState(
        val loading: Boolean = false,
        val phoneNumbers: List<PhoneNumber> = emptyList()
    )

}

enum class SMSPermission{
    PERMISSION_GRANTED,
    PERMISSION_DENIED,
    PERMISSION_NOT_REQUESTED
}