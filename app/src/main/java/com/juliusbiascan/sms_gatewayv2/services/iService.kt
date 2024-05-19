package com.juliusbiascan.sms_gatewayv2.services

import android.app.AlarmManager
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.IBinder
import android.os.SystemClock
import android.telephony.SmsManager
import com.juliusbiascan.sms_gatewayv2.R
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.WebSocket
import okhttp3.WebSocketListener
import org.json.JSONObject

class iService : Service() {

    private val CHANNEL_ID = "WebSocketForegroundServiceChannel"
    private val NOTIFICATION_ID = 1
    private var webSocket: WebSocket? = null

    override fun onTaskRemoved(rootIntent: Intent) {
        val restartServiceIntent = Intent(applicationContext, iService::class.java).also {
            it.setPackage(packageName)
        }
        val restartServicePendingIntent: PendingIntent = PendingIntent.getService(this, 1, restartServiceIntent,
            PendingIntent.FLAG_ONE_SHOT or PendingIntent.FLAG_IMMUTABLE)
        applicationContext.getSystemService(Context.ALARM_SERVICE)
        val alarmService: AlarmManager = applicationContext.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarmService.set(AlarmManager.ELAPSED_REALTIME, SystemClock.elapsedRealtime() + 1000, restartServicePendingIntent)
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        // Create a notification channel for Android Oreo and above
        createNotificationChannel()


        // Create a WebSocket client
        val client = OkHttpClient()
        val request = Request.Builder()
            .url("wss://free.blr2.piesocket.com/v3/1?api_key=QvZ6JGC82IwzzF0iOeJo6iJJ7UFWz2whfOWGoGlD&notify_self=1")
            .build()
        val listener = EchoWebSocketListener()

        webSocket = client.newWebSocket(request, listener)

        return START_STICKY
    }

    override fun onDestroy() {
        webSocket?.close(1000, null)
        super.onDestroy()
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                "SMS Gateway Service",
                NotificationManager.IMPORTANCE_HIGH
            )
            channel.lockscreenVisibility = Notification.VISIBILITY_PUBLIC
            channel.enableLights(true)
            channel.lightColor = Color.RED
            channel.enableVibration(true)
            channel.vibrationPattern = longArrayOf(100, 200, 300, 400, 500, 400, 300, 200, 400)

            val notificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    private fun createNotification(content: String): Notification {
        val builder = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Notification.Builder(this, CHANNEL_ID)
        } else {
            Notification.Builder(this)
        }

        builder.setContentTitle("SMS Gateway Service")
            .setContentText(content)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setOngoing(true)
            .setCategory(Notification.CATEGORY_SERVICE)

        return builder.build()
    }

    private inner class EchoWebSocketListener : WebSocketListener() {

        override fun onOpen(webSocket: WebSocket, response: okhttp3.Response) {
            // Send a message to the server
        }

        override fun onMessage(webSocket: WebSocket, text: String) {
            // Log the message received from the server
            println("Received message: $text")
            // Received a text message
            val data = JSONObject(text)

            // on the below line we are creating a try and catch block
            try {
                val smsManager: SmsManager = this@iService.getSystemService(
                    SmsManager::class.java
                )
                // on below line we are sending text message.
                smsManager.sendTextMessage(
                    data.getString("receiver"),
                    null,
                    data.getString("message"),
                    null,
                    null
                )
                // Create a notification
                val notification = createNotification("Message Sent")

                // Start the foreground service
                startForeground(NOTIFICATION_ID, notification)
                // on below line we are displaying a toast message for message send,
                println("Message Sent")
            } catch (e: Exception) {
                // on catch block we are displaying toast message for error.
               println( e.message.toString())
            }
        }

        override fun onClosing(webSocket: WebSocket, code: Int, reason: String) {
            // Log the closing message
            println("Closing WebSocket: $code $reason")
        }

        override fun onFailure(webSocket: WebSocket, t: Throwable, response: okhttp3.Response?) {
            // Log the error
            println("WebSocket error: ${t.message}")
        }
    }
}