package com.example.chat_mobile_interface.service

import com.example.chat_mobile_interface.model.UserHandleDto
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class FriendRepo {
    suspend fun getFriends(): List<UserHandleDto> {
            var response=GlobalScope.async {
                var da = ""
                val tcpClient = TcpClient("192.168.0.101",
                    1300, "gfr: 2", object : TcpClient.OnMessageReceivedListener {
                        override fun onMessageReceived(message: String) {
                            da = message
                            println(message)
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