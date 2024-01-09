package com.example.chat_mobile_interface.view.model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.chat_mobile_interface.model.UserHandleDto
import com.example.chat_mobile_interface.repository.FriendRepo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class FriendViewModel() : ViewModel() {
    private val friendRepo = FriendRepo()
    private val _dataFlow = MutableStateFlow<List<UserHandleDto>>(emptyList())
    val dataFlow: StateFlow<List<UserHandleDto>> = _dataFlow
    fun getFriendsUserHandle(
        id: Int?
    ) {
        viewModelScope.launch{
            val da =friendRepo.getFriends()
            _dataFlow.value=da
            println("dataflow+"+_dataFlow.value)
        }
    }
}