package com.example.chat_mobile_interface.view.model

import androidx.lifecycle.ViewModel
import com.example.chat_mobile_interface.model.UserHandleDto
import com.example.chat_mobile_interface.service.TcpClient
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class FriendViewModel(var friends :List<UserHandleDto> ): ViewModel() {

    fun getFriendsUserHandle(
        id: Int?
    ){
        var response: String = ""
        val tcpClient = TcpClient("192.168.0.102",
            1300, "gfr: 2", object : TcpClient.OnMessageReceivedListener {
                override fun onMessageReceived(message: String) {
                    response = message
                }
            })
        tcpClient.execute()
        val regex = Regex("""UserHandleDto\[id=(\d+), firstName=([^\]]+), familyName=([^\]]+)]""")
        // Find all matches in the input string
        val matches = regex.findAll(response)

        // Create a list of UserHandleDto objects from the matches
        val userHandleDtoList = matches.map {
            val (id, firstName, familyName) = it.destructured
            UserHandleDto(id.toInt(), firstName, familyName)
        }.toList()
        friends= userHandleDtoList
    }
}