package com.example.chat_mobile_interface.service

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.IBinder
import androidx.core.app.NotificationCompat
import com.example.chat_mobile_interface.R
import com.example.chat_mobile_interface.model.UnreadMessage
import com.example.chat_mobile_interface.repository.UnityChatRepo
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.ArrayList

class MessageService() : Service() {
    private val repo = UnityChatRepo()
    private val serviceScope = CoroutineScope(Dispatchers.Default)

    override fun onBind(intent: Intent?): IBinder? {
        return null;
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val userId = intent?.getIntExtra("userId", 0)
        when (intent?.action) {
            Actions.START.toString() -> userId?.let { start(it) }
            Actions.STOP.toString() -> stopSelf()
        }
        return super.onStartCommand(intent, flags, startId)
    }

    private fun start(userId: Int) {
        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        val notification = NotificationCompat.Builder(this, "ms_channel")
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle("Messaging app is running in the backround")
            .setContentText("")
            .build()
        startForeground(1, notification)
        val channel = NotificationChannel(
            "ms_channel",
            "Chat Messages",
            NotificationManager.IMPORTANCE_DEFAULT
        )
        notificationManager.createNotificationChannel(channel)
        val context: Context = this
        var temp = emptyList<UnreadMessage>()
        serviceScope.launch {
            while (true) {
                delay(20000L)
                var unreadMs = repo.getUnreadMs(userId)
                if (unreadMs != null) {
                    if (unreadMs.size != 0) {
                        if (!unreadMs.equals(temp)) {
                            temp=unreadMs
                            var br = 2
                            unreadMs.forEach { ms ->
                                val notification = NotificationCompat.Builder(context, "ms_channel")
                                    .setContentTitle(ms.userSender)
                                    .setContentText(ms.content)
                                    .setSmallIcon(R.drawable.chat2)
                                    .build()
                                notificationManager.notify(br, notification)
                                br++
                            }
                        }
                        /*
                        if (unreadMs.size != 0) {
                            val notification = NotificationCompat.Builder(context, "ms_channel")
                                .setContentTitle(unreadMs.get(0).userSender)
                                .setContentText(unreadMs.get(0).content)
                                .setSmallIcon(R.drawable.chat2)
                                .build()
                            notificationManager.notify(1, notification)
                        }


                         */
                    }
                }
                //delay(100000L)
            }
        }

    }

    enum class Actions {
        START, STOP
    }
}