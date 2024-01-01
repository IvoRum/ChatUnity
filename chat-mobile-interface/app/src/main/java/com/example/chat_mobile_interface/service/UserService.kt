package com.example.chat_mobile_interface.service

import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.example.chat_mobile_interface.model.UserHandleDto
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.io.OutputStream
import java.net.Socket

class UserService {

    private lateinit var socket: Socket
    private lateinit var output: OutputStream
    private lateinit var input: BufferedReader

    fun getUserHandle(id:Int?): UserHandleDto {
        GlobalScope.launch(Dispatchers.IO) {
            connectToServer("your_server_ip", 1300) // Replace with your server details
            sendMessage("Hello, Server!")
            receiveMessage()
        }
        try {
            socket.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return UserHandleDto(1,"Ivancho","Panov")
    }


    private fun connectToServer(serverIp: String, serverPort: Int) {
        try {
            socket = Socket(serverIp, serverPort)
            output = socket.getOutputStream()
            input = BufferedReader(InputStreamReader(socket.getInputStream()))
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    private fun sendMessage(message: String) {
        try {
            output.write(message.toByteArray())
            output.flush()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    private fun receiveMessage() {
        try {
            while (true) {
                val message = input.readLine()
                if (message != null) {
                    // Handle the received message on the UI thread
                    Handler(Looper.getMainLooper()).post {
                        // Update UI or perform any other actions with the received message
                    }
                }
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }
}

