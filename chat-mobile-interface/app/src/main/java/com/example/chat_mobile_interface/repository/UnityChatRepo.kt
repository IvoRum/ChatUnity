package com.example.chat_mobile_interface.repository

import com.example.chat_mobile_interface.model.GroupDto
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
    val SERVER_ADDRESS = "172.28.104.40"//"192.168.0.104"
    suspend fun getFriends(userId: Int): List<UserHandleDto>? {
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
            if (response.await().equals(null)) {
                null
            } else {
                val regex =
                    Regex("""UserHandleDto\[id=(\d+), firstName=([^\]]+), familyName=([^\]]+), conversation=(\d+)]""")
                // Find all matches in the input string
                val matches = response.await()?.let { regex.findAll(it) }

                // Create a list of UserHandleDto objects from the matches
                val userHandleDtoList = matches?.map {
                    val (id, firstName, familyName, conversation) = it.destructured
                    UserHandleDto(id.toInt(), firstName, familyName, conversation.toInt())
                }?.toList()
                userHandleDtoList
            }
        }
    }

    suspend fun getGroups(userId: Int): List<GroupDto>? {
        var response = GlobalScope.async {
            var da = ""
            val tcpClient = TcpClient(SERVER_ADDRESS,
                1300, "gug: $userId", object : TcpClient.OnMessageReceivedListener {
                    override fun onMessageReceived(message: String) {
                        da = message
                        //println(message)
                    }
                })
            tcpClient.execute()
        }
        return runBlocking {
            if (response.await().equals(null)) {
                null
            } else {
                val regex =
                    Regex("""GroupDto\[name=([^\]]+), id=(\d+)]""")
                // Find all matches in the input string
                val matches = response.await()?.let { regex.findAll(it) }

                // Create a list of UserHandleDto objects from the matches
                val userHandleDtoList = matches?.map {
                    val (name, id) = it.destructured
                    GroupDto(name, id.toInt())
                }?.toList()
                userHandleDtoList
            }
        }
    }

    suspend fun getMessages(userId: Int, coversation: Int): List<MessageReachedPointDto>? {
        var response = GlobalScope.async {
            var da = ""
            val tcpClient = TcpClient(SERVER_ADDRESS,
                1300, "gms: $userId $coversation@0", object : TcpClient.OnMessageReceivedListener {
                    override fun onMessageReceived(message: String) {
                        da = message
                        println(message)
                    }
                })
            tcpClient.execute()
        }
        return runBlocking {
            if (response.await() != null) {
                val regex =
                    Regex("""MessageReachedPointDto\[firstName=([^\]]+), idSender=(\d+), idReceiver=(\d+), messageOrder=(\d+), content=([^\]]+)""")
                // Find all matches in the input string
                val matches = regex.findAll(response.await()!!)

                // Create a list of UserHandleDto objects from the matches
                val foundMessages = matches.map {
                    val (firstName, id, idReceiver, messageOrder, content) = it.destructured
                    MessageReachedPointDto(
                        firstName,
                        id.toInt(),
                        idReceiver.toInt(),
                        messageOrder.toInt(),
                        content
                    )
                }.toList()
                foundMessages
            } else {
                null
            }
        }
    }

    suspend fun sendMessages(sender: Int, conversation: Int, order: Int, message: String): Boolean {
        println("Message: sende: $sender; Coversation: $conversation; Content:$message; Order:$order")
        var response = GlobalScope.async {
            var da = ""
            val tcpClient = TcpClient(SERVER_ADDRESS,
                1300,
                "sms: $sender $conversation $order $$message",
                object : TcpClient.OnMessageReceivedListener {
                    override fun onMessageReceived(message: String) {
                        da = message
                        println(message)
                    }
                })
            tcpClient.execute()
        }
        return runBlocking {
            if (response.await().equals(null)) {
                false
            } else {
                println(response.await())
                true
            }
        }
    }

    suspend fun logIn(email: String, password: String): LogdInUser? {
        var response = GlobalScope.async {
            var serverResponce = ""
            val tcpClient = TcpClient(SERVER_ADDRESS,
                1300, "log: $email $password", object : TcpClient.OnMessageReceivedListener {
                    override fun onMessageReceived(message: String) {
                        serverResponce = message
                    }
                })
            tcpClient.execute()
        }
        return runBlocking {
            if (response.await().equals("null")) {
                LogdInUser(404, "", "", "")
            } else {
                if (response.await() == null) {
                    null
                } else {
                    val regex =
                        Regex("""LogdInUser\[id=(\d+), firstName=([A-Za-z]+), familyName=([A-Za-z]+), email=([A-Za-z@.]+)\]""")
                    val matches = response.await()?.let { regex.findAll(it) }

                    val userHandleDtoList = matches?.map {
                        val (id, firstName, familyName, email) = it.destructured
                        LogdInUser(id.toInt(), firstName, familyName, email)
                    }?.toList()
                    userHandleDtoList?.get(0)
                }
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

    fun execute(): String? {
        var socket: Socket? = null
        try {
            socket = Socket(serverIp, serverPort)
        } catch (e: Exception) {
            return null
        }
        try {
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