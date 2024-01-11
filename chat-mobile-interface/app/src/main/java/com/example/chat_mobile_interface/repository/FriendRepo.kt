package com.example.chat_mobile_interface.repository

import com.example.chat_mobile_interface.model.Message
import com.example.chat_mobile_interface.model.MessageReachedPointDto
import com.example.chat_mobile_interface.model.UserHandleDto
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.io.PrintWriter
import java.net.InetAddress
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

    suspend fun getMessages(): List<MessageReachedPointDto> {
        var response=GlobalScope.async {
            var da = ""
            val tcpClient = TcpClient("192.168.0.104",
                1300, "gms: 1 1@3", object : TcpClient.OnMessageReceivedListener {
                    override fun onMessageReceived(message: String) {
                        da = message
                        println(message)
                    }
                })
            tcpClient.execute()
        }
        return runBlocking {
            val regex =
                Regex("""MessageReachedPointDto\[idSender=(\d+), idReceiver=(\d+), content=([^\]]+)""")
            // Find all matches in the input string
            val matches = regex.findAll(response.await())

            // Create a list of UserHandleDto objects from the matches
            val foundMessages = matches.map {
                val (id, idReceiver, content) = it.destructured
                MessageReachedPointDto(id.toInt(), idReceiver.toInt(), content)
            }.toList()
            foundMessages
        }
    }

    suspend fun sendMessages(message: String) {
        var response= GlobalScope.async {
            var da = ""
            val tcpClient = TcpClient("192.168.0.104",
                1300, "sms: 1 1 $message", object : TcpClient.OnMessageReceivedListener {
                    override fun onMessageReceived(message: String) {
                        da = message
                        println(message)
                    }
                })
            tcpClient.execute()
        }
        return runBlocking {
            println(response.await())
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
            socket = Socket(serverIp, serverPort)

            val out = PrintWriter(socket.getOutputStream(), true)
            out.println(messageToSend)

            val `in` = BufferedReader(InputStreamReader(socket.getInputStream()))
            receivedMessage = `in`.readLine()

        } catch (e: IOException) {
            e.printStackTrace()
            println("Vruskata grumna")
        } finally {
            socket?.close()
        }
        receivedMessage?.let { listener.onMessageReceived(it) }
        return receivedMessage.toString()
    }

    interface OnMessageReceivedListener {
        fun onMessageReceived(message: String)
    }
}