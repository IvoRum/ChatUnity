package com.example.chat_mobile_interface.service

import android.app.Service
import android.content.Intent
import android.os.IBinder
import com.example.chat_mobile_interface.model.UserHandleDto
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.io.PrintWriter
import java.net.Socket


class UserService : Service() {
    private var name = ""
    fun getUserHandle(id: Int?
    ): UserHandleDto {
        val tcpClient = TcpClient("192.168.0.102",
            1300, "Hello, Server!", object : TcpClient.OnMessageReceivedListener {
                override fun onMessageReceived(message: String) {
                    name = message
                }
            })

        tcpClient.execute()
        return UserHandleDto(1, name, "Panov")
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null;
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val tcpClient = TcpClient("192.168.0.102",
            1300, "Hello, Server!", object : TcpClient.OnMessageReceivedListener {
                override fun onMessageReceived(message: String) {
                    name = message
                }
            })

        tcpClient.execute()
        //return START_STICKY
        return super.onStartCommand(intent, flags, startId)
    }
}

class TcpClient(
    private val serverIp: String,
    private val serverPort: Int,
    private val messageToSend: String,
    private val listener: OnMessageReceivedListener
) {

    private var receivedMessage: String? = null

    fun execute() {
        GlobalScope.launch(Dispatchers.IO) {
            var socket: Socket? = null
            try {
                // Create a socket connection
                socket = Socket(serverIp, serverPort)

                // Send the message to the server
                val out = PrintWriter(socket.getOutputStream(), true)
                out.println(messageToSend)

                // Receive the response from the server
                val `in` = BufferedReader(InputStreamReader(socket.getInputStream()))
                receivedMessage = `in`.readLine()

            } catch (e: IOException) {
                e.printStackTrace()
            } finally {
                socket?.close()
            }

            // Notify the listener with the received message
            receivedMessage?.let { listener.onMessageReceived(it) }
        }
    }

    interface OnMessageReceivedListener {
        fun onMessageReceived(message: String)
    }
}
