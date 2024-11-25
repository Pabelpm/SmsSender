package com.pabloprieto.smssender.ui.home

import android.Manifest
import android.annotation.SuppressLint
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.pabloprieto.smssender.ui.theme.SmsSenderTheme


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun HomeScreen(
    vm: HomeViewModel = viewModel { HomeViewModel() }
) {
    //Permissions
    var permissionStatus by remember { mutableStateOf(SMSPermission.PERMISSION_DENIED) }
    permissionStatus = if ( vm.isPermissionGranted(Manifest.permission.SEND_SMS)) SMSPermission.PERMISSION_GRANTED else SMSPermission.PERMISSION_DENIED

    val permissionLauncher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.RequestPermission()) { granted ->
            permissionStatus = if (granted) SMSPermission.PERMISSION_GRANTED else SMSPermission.PERMISSION_DENIED

        }

    val phoneNumberState = vm.phoneNumberState

    var text by remember { mutableStateOf("") }
    var enableTextField by remember { mutableStateOf(true) }
    var textToSend = text
    SmsSenderTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Button(
                    onClick = { permissionLauncher.launch(Manifest.permission.SEND_SMS) },
                    enabled = !vm.isPermissionGranted(Manifest.permission.SEND_SMS)
                ) {
                    Text(text = "Request permission")
                }
                Text(text = vm.getStringFromEnum(permissionStatus))
                TextField(
                    modifier = Modifier.padding(top = 30.dp),
                    value = text,
                    onValueChange = {
                        text = it
                    },
                    label = { Text("Write some message before send Sms") },
                    enabled = enableTextField

                )
                Button(
                    modifier = Modifier.padding(top= 32.dp),
                    onClick = {
                        enableTextField = false
                        vm.clickRetrieveNumbers()  },
                    enabled = vm.isPermissionGranted(Manifest.permission.SEND_SMS)
                ) {
                    Text(text = "Get all phone numbers and send sms")
                }

                if (phoneNumberState.loading) {
                    CircularProgressIndicator()
                }
                if(phoneNumberState.phoneNumbers.isNotEmpty()){
                    Text(text = "All phone numbers retrieved : ${phoneNumberState.phoneNumbers.first().prefix} ${phoneNumberState.phoneNumbers.first().number}")
                    textToSend = textToSend.ifEmpty { "Blank message" }
                    vm.sendSMS(LocalContext.current,phoneNumberState.phoneNumbers.first().number,textToSend)
                }
            }
        }
    }}

@Preview(showBackground = true)
@Composable
fun Preview() {
    SmsSenderTheme {
        HomeScreen()
    }
}