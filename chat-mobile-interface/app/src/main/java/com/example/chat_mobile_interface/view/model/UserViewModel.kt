package com.example.chat_mobile_interface.view.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class UserViewModel(val userId: String) : ViewModel() {
    private val _receivedMessage = MutableLiveData<String>()
    val receivedMessage: LiveData<String> get() = _receivedMessage

    // Function to start the service
    fun startTcpService() {
        //val serviceIntent = Intent(this, UserService::class.java)
        //startService(serviceIntent)
    }

    // Function to update the LiveData with received message
    fun updateReceivedMessage(message: String) {
        _receivedMessage.value = message
    }
}