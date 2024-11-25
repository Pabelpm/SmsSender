package com.pabloprieto.smssender.ui.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pabloprieto.smssender.data.PhoneNumber
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

    fun clickRetrieveNumbers() {
        viewModelScope.launch {
            state = UiState(loading = true)
            delay(1000)
            state = UiState(loading = false, phoneNumbers = arrayListOf(fakeNumber))
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