package com.pabloprieto.smssender

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text as TextM
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
import com.pabloprieto.smssender.ui.theme.SmsSenderTheme

class MainActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            var permissionStatus by remember { mutableStateOf("Permission not requested") }
            val permissionLauncher =
                rememberLauncherForActivityResult(contract = ActivityResultContracts.RequestPermission()) { granted ->
                    permissionStatus = if (granted) "Permission granted" else "Permission denied"

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
                        permissionStatus = if (granted) "Permission granted" else "Permission denied"
                        Button(
                            onClick = { permissionLauncher.launch(Manifest.permission.SEND_SMS) },
                            enabled = !granted
                        ) {
                            TextM(text = "Request permission")
                        }
                        TextM(text = permissionStatus)

                        Button(
                            modifier = Modifier.padding(top= 32.dp),
                            onClick = {                             },
                            enabled = granted
                        ) {
                            TextM(text = "Get all phone numbers and send sms")
                        }
                    }
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun Preview() {
    SmsSenderTheme {
    }
}