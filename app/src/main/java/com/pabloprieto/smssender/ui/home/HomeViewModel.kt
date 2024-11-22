package com.pabloprieto.smssender.ui.home

import androidx.lifecycle.ViewModel

class HomeViewModel : ViewModel() {

    fun getStringFromEnum(enum: SMSPermission):String{
        return when (enum){
            SMSPermission.PERMISSION_GRANTED -> "Permission granted"
            SMSPermission.PERMISSION_DENIED -> "Permission denied"
            SMSPermission.PERMISSION_NOT_REQUESTED -> "Permission not requested"
        }
    }
}

enum class SMSPermission{
    PERMISSION_GRANTED,
    PERMISSION_DENIED,
    PERMISSION_NOT_REQUESTED
}