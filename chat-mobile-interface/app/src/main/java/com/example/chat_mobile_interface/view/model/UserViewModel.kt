package com.example.chat_mobile_interface.view.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.chat_mobile_interface.service.TcpClient

class UserViewModel(val userId: String) : ViewModel() {
    private val _receivedMessage = MutableLiveData<String>()
    val receivedMessage: LiveData<String> get() = _receivedMessage


    fun getUserInformation() {
        val tcpClient = TcpClient("192.168.0.102",
            1300, "", object : TcpClient.OnMessageReceivedListener {
                override fun onMessageReceived(message: String) {

                }
            })

        tcpClient.execute()
    }

    // Function to update the LiveData with received message
    fun updateReceivedMessage(message: String) {
        _receivedMessage.value = message
    }
}