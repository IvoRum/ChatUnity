package com.example.chat_mobile_interface.repository

import com.example.chat_mobile_interface.model.UserHandleDto
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.io.PrintWriter
import java.net.Socket

class FriendRepo {
    suspend fun getFriends(): List<UserHandleDto> {
            var response=GlobalScope.async {
                var da = ""
                val tcpClient = TcpClient("192.168.0.104",
                    1300, "gfr: 2", object : TcpClient.OnMessageReceivedListener {
                        override fun onMessageReceived(message: String) {
                            da = message
                            //println(message)
                        }
                    })
                tcpClient.execute()
            }
        return runBlocking {
            val regex =
                Regex("""UserHandleDto\[id=(\d+), firstName=([^\]]+), familyName=([^\]]+)]""")
            // Find all matches in the input string
            val matches = regex.findAll(response.await())

            // Create a list of UserHandleDto objects from the matches
            val userHandleDtoList = matches.map {
                val (id, firstName, familyName) = it.destructured
                UserHandleDto(id.toInt(), firstName, familyName)
            }.toList()
            userHandleDtoList
        }
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