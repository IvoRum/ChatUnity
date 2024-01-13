package com.example.chat_mobile_interface.view.model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.chat_mobile_interface.model.LogdInUser
import com.example.chat_mobile_interface.model.MessageReachedPointDto
import com.example.chat_mobile_interface.repository.UnityChatRepo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class UserViewModel : ViewModel() {
    private val friendRepo = UnityChatRepo()
    private val _dataFlow = MutableStateFlow<List<MessageReachedPointDto>>(emptyList())
    val dataFlow: StateFlow<List<MessageReachedPointDto>> = _dataFlow
    private val _logedDataFlow=MutableStateFlow<LogdInUser>(LogdInUser(0,"","",""))
    val logedDataFlow:StateFlow<LogdInUser> = _logedDataFlow
    fun getUserMessages(){
        viewModelScope.launch {
            val da = friendRepo.getMessages()
            _dataFlow.value = da
            println("User Message:" + _dataFlow.value)
        }
    }

    fun sendMessage(sender: Int, conversation: Int, order: Int, message: String){
        viewModelScope.launch{
            friendRepo.sendMessages(sender,conversation,order,message)
        }
    }

    fun logUser(email:String, password:String){
        viewModelScope.launch {
            _logedDataFlow.value=friendRepo.logIn(email,password)
            println("Log in process finished")
        }
    }
}