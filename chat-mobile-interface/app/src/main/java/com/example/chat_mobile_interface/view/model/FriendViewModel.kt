package com.example.chat_mobile_interface.view.model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.chat_mobile_interface.model.LogdInUser
import com.example.chat_mobile_interface.model.UserHandleDto
import com.example.chat_mobile_interface.repository.UnityChatRepo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class FriendViewModel(val userId:Int) : ViewModel() {
    private val friendRepo = UnityChatRepo()
    private val _dataFlow = MutableStateFlow<List<UserHandleDto>>(emptyList())
    val dataFlow: StateFlow<List<UserHandleDto>> = _dataFlow

    fun getFriendsUserHandle(
        id: Int?
    ) {
        viewModelScope.launch{
            val da =friendRepo.getFriends(userId)
            _dataFlow.value=da
            println("UserFriends: "+_dataFlow.value)
        }
    }
}