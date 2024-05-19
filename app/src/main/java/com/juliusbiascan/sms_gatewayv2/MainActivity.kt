package com.juliusbiascan.sms_gatewayv2

import android.app.ActivityManager
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.tooling.preview.Preview
import com.juliusbiascan.sms_gatewayv2.services.iService
import com.juliusbiascan.sms_gatewayv2.ui.components.ConnectivityStatus
import com.juliusbiascan.sms_gatewayv2.ui.screens.MainScreen
import com.juliusbiascan.sms_gatewayv2.ui.theme.SMSGatewayv2Theme

class MainActivity : ComponentActivity() {

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        pushNotificationPermissionLauncher.launch(android.Manifest.permission.POST_NOTIFICATIONS)

        setContent {
            SMSGatewayv2Theme {
                Surface(color = MaterialTheme.colorScheme.background) {
                    MainPage()
                }
            }
        }
    }

    private val pushNotificationPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { _ ->

    }

    private fun isMyServiceRunning(mClass: Class<iService>): Boolean {
        val manager: ActivityManager = getSystemService(ACTIVITY_SERVICE) as ActivityManager

        @Suppress("DEPRECATION")
        for (service: ActivityManager.RunningServiceInfo in manager.getRunningServices(Int.MAX_VALUE)) {
            if (mClass.name.equals(service.service.className)) {
                return true
            }
        }
        return false
    }

    @Preview(showBackground = true)
    @Composable
    fun MainPage() {

        var status by remember { mutableStateOf( if (isMyServiceRunning(iService::class.java)) ConnectivityStatus.CONNECTED else ConnectivityStatus.NONE) }

        MainScreen(
            connectivityStatus = status,
            onToAboutScreen = {

            },
            onConnectClick = {
                val timer = object: CountDownTimer(5000, 1000) {
                    override fun onTick(millisUntilFinished: Long) {
                        status = ConnectivityStatus.CONNECTING
                    }

                    override fun onFinish() {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                            startForegroundService(Intent(this@MainActivity, iService::class.java))
                        } else
                            startService(Intent(this@MainActivity, iService::class.java))
                        status = ConnectivityStatus.CONNECTED
                    }
                }

                timer.start()

            },
            onDisconnect = {
                stopService(Intent(this, iService::class.java))
                status = ConnectivityStatus.DISCONNECT
            }) {
        }
    }
}