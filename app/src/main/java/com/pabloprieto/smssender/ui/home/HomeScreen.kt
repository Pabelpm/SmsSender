package com.pabloprieto.smssender.ui.home

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.telephony.SmsManager
import android.widget.Toast
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
import androidx.core.content.ContextCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import com.pabloprieto.smssender.ui.theme.SmsSenderTheme


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun HomeScreen(
    vm: HomeViewModel = viewModel { HomeViewModel() }
) {
    val state = vm.state
    var permissionStatus by remember { mutableStateOf(SMSPermission.PERMISSION_NOT_REQUESTED) }
    val permissionLauncher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.RequestPermission()) { granted ->
            permissionStatus = if (granted) SMSPermission.PERMISSION_GRANTED else SMSPermission.PERMISSION_DENIED

        }
    SmsSenderTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                val granted: Boolean = ContextCompat.checkSelfPermission(
                    LocalContext.current,
                    Manifest.permission.SEND_SMS
                ) == PackageManager.PERMISSION_GRANTED
                permissionStatus = if (granted) SMSPermission.PERMISSION_GRANTED else SMSPermission.PERMISSION_DENIED
                Button(
                    onClick = { permissionLauncher.launch(Manifest.permission.SEND_SMS) },
                    enabled = !granted
                ) {
                    Text(text = "Request permission")
                }
                Text(text = vm.getStringFromEnum(permissionStatus))

                Button(
                    modifier = Modifier.padding(top= 32.dp),
                    onClick = { vm.clickRetrieveNumbers()  },
                    enabled = granted
                ) {
                    Text(text = "Get all phone numbers and send sms")
                }

                if (state.loading) {
                    CircularProgressIndicator()
                }
                if(state.phoneNumbers.isNotEmpty()){
                    Text(text = "All phone numbers retrieved : ${state.phoneNumbers.first().prefix} ${state.phoneNumbers.first().number}")
                    vm.sendSMS(LocalContext.current,state.phoneNumbers.first().number,"Example text")
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