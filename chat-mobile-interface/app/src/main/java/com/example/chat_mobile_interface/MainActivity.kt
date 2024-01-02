package com.example.chat_mobile_interface

import android.Manifest
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.core.app.ActivityCompat
import com.example.chat_mobile_interface.service.RunningService
import com.example.chat_mobile_interface.ui.theme.ChatmobileinterfaceTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(
                    Manifest.permission.POST_NOTIFICATIONS,
                    Manifest.permission.FOREGROUND_SERVICE_REMOTE_MESSAGING
                ),
                0
            )
        }
        setContent {
            ChatmobileinterfaceTheme {
                Button(onClick = {
                    val intent = Intent(this, FriendList::class.java)
                    Intent(applicationContext, RunningService::class.java).also {
                        it.action = RunningService.Actions.START.toString()
                        startService(it)
                    }
                    //startActivity(intent)
                }) {
                    Text(text = "Open friend list")
                }
            }
        }
    }
}