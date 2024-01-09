package com.example.chat_mobile_interface.view.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.chat_mobile_interface.model.MessageReachedPointDto
import com.example.chat_mobile_interface.model.UserHandleDto
import com.example.chat_mobile_interface.repository.FriendRepo
import com.example.chat_mobile_interface.repository.TcpClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class UserViewModel(val userId: String,val userName:String) : ViewModel() {
    private val friendRepo = FriendRepo()
    private val _dataFlow = MutableStateFlow<List<MessageReachedPointDto>>(emptyList())
    val dataFlow: StateFlow<List<MessageReachedPointDto>> = _dataFlow

    fun getUserMessages(){
        viewModelScope.launch {
            val da = friendRepo.getMessages()
            _dataFlow.value = da
            println("dataflow+" + _dataFlow.value)
        }
    }
}