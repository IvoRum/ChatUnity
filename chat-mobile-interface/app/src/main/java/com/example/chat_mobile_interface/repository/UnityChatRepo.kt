package com.example.chat_mobile_interface.repository

import com.example.chat_mobile_interface.model.LogdInUser
import com.example.chat_mobile_interface.model.MessageReachedPointDto
import com.example.chat_mobile_interface.model.UserHandleDto
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.io.PrintWriter
import java.net.Socket
import kotlin.math.log

class UnityChatRepo {
    val SERVER_ADDRESS = "192.168.0.104"
    suspend fun getFriends(userId:Int): List<UserHandleDto> {
        var response = GlobalScope.async {
            var da = ""
            val tcpClient = TcpClient(SERVER_ADDRESS,
                1300, "gfr: $userId", object : TcpClient.OnMessageReceivedListener {
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
        var response = GlobalScope.async {
            var da = ""
            val tcpClient = TcpClient(SERVER_ADDRESS,
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
        var response = GlobalScope.async {
            var da = ""
            val tcpClient = TcpClient(SERVER_ADDRESS,
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

    suspend fun logIn(email: String, password: String): LogdInUser {
        var response = GlobalScope.async {
            var serverResponce = ""
            val tcpClient = TcpClient(SERVER_ADDRESS,
                1301, "log: $email $password", object : TcpClient.OnMessageReceivedListener {
                    override fun onMessageReceived(message: String) {
                        serverResponce = message
                    }
                })
            tcpClient.execute()
        }
        return runBlocking {
            if(response.await().equals("null")){
                 LogdInUser(404,"","","")
            }else{

            val regex =
                Regex("""LogdInUser\[id=(\d+), firstName=([A-Za-z]+), familyName=([A-Za-z]+), email=([A-Za-z@.]+)\]""")
            val matches = regex.findAll(response.await())

            val userHandleDtoList = matches.map {
                val (id, firstName, familyName, email) = it.destructured
                LogdInUser(id.toInt(), firstName, familyName, email)
            }.toList()
            userHandleDtoList.get(0)
            }
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