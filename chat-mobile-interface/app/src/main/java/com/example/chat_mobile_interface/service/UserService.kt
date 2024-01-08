package com.example.chat_mobile_interface.service

import android.app.Service
import android.content.Intent
import android.os.IBinder
import com.example.chat_mobile_interface.model.UserHandleDto
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.io.PrintWriter
import java.net.Socket


class UserService : Service() {
    private var name = ""
    fun getUserHandle(
        id: Int?
    ): UserHandleDto {
        val tcpClient = TcpClient("192.168.0.102",
            1300, "Hello, Server!", object : TcpClient.OnMessageReceivedListener {
                override fun onMessageReceived(message: String) {
                    name = message
                }
            })

        //tcpClient.execute()
        return UserHandleDto(1, name, "Panov")
    }

    suspend fun getFriendsUserHandle(
        id: Int?
    ): List<UserHandleDto> {
        var response: String = ""
        val tcpClient = TcpClient("192.168.0.102",
            1300, "gfr: 2", object : TcpClient.OnMessageReceivedListener {
                override fun onMessageReceived(message: String) {
                    response = message
                    println(message)
                }
            })
        tcpClient.execute()
        // val removedPrefix: List<String> = response.split("[UserHandleDto[")
        //val removedPrefix:
        val onCompleteListener = object : TcpClient.OnMessageReceivedListener {
            override fun onMessageReceived(message: String) {
                TODO("Not yet implemented")
            }
        }
        val regex = Regex("""UserHandleDto\[id=(\d+), firstName=([^\]]+), familyName=([^\]]+)]""")
        // Find all matches in the input string
        val matches = regex.findAll(response)

        // Create a list of UserHandleDto objects from the matches
        val userHandleDtoList = matches.map {
            val (id, firstName, familyName) = it.destructured
            UserHandleDto(id.toInt(), firstName, familyName)
        }.toList()
        return userHandleDtoList
    }


    override fun onBind(intent: Intent?): IBinder? {
        return null;
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
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

    fun execute(): String {
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
        receivedMessage?.let { listener.onMessageReceived(it) }
        return receivedMessage.toString()
            // Notify the listener with the received message
         //

    }

    interface OnMessageReceivedListener {
        fun onMessageReceived(message: String)
    }
}

class TcpClient1(
    private val serverIp: String,
    private val serverPort: Int,
    private val messageToSend: String
) {

    private var receivedMessage: String? = null

    suspend fun execute(): List<UserHandleDto> = withContext(Dispatchers.IO) {
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

        val onCompleteListener = object : TcpClient.OnMessageReceivedListener {
            override fun onMessageReceived(message: String) {
                TODO("Not yet implemented")
            }
        }
        val regex = Regex("""UserHandleDto\[id=(\d+), firstName=([^\]]+), familyName=([^\]]+)]""")
        // Find all matches in the input string
        val matches = receivedMessage?.let { regex.findAll(it) }

        // Create a list of UserHandleDto objects from the matches
        val userHandleDtoList = matches?.map {
            val (id, firstName, familyName) = it.destructured
            UserHandleDto(id.toInt(), firstName, familyName)
        }?.toList()
        return@withContext userHandleDtoList!!

        // Process the received message or handle the result as needed
        //return@withContext receivedMessage?.let { listOf(it) } ?: emptyList()
    }
}

