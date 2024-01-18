package com.example.chat_mobile_interface.service

import android.app.Service
import android.content.Intent
import android.os.IBinder
import androidx.core.app.NotificationCompat
import com.example.chat_mobile_interface.R

class MessageService : Service() {
    override fun onBind(intent: Intent?): IBinder? {
        return null;
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        when (intent?.action) {
            Actions.START.toString() -> start()
            Actions.STOP.toString() -> stopSelf()
        }
        return super.onStartCommand(intent, flags, startId)
    }

    private fun start() {

        val notification = NotificationCompat.Builder(this, "ms_channel")
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle("New MS!!!")
            .setContentText("Time 5:00")
            .build()
        startForeground(1,notification)
    }

    enum class Actions {
        START, STOP
    }
}