package com.example.chat_mobile_interface.view.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.chat_mobile_interface.model.UserHandleDto
import com.example.chat_mobile_interface.service.FriendRepo
import com.example.chat_mobile_interface.service.TcpClient
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import java.util.ArrayList

class FriendViewModel(): ViewModel() {
    private val friendRepo=FriendRepo()
    private val _friends= MutableLiveData<List<UserHandleDto>>()
    val friendList:LiveData<List<UserHandleDto>> get()=_friends
    var simplefriends = emptyList<UserHandleDto>()
     fun getFriendsUserHandle(
        id: Int?
    ){
        viewModelScope.async{
            val da= friendRepo.getFriends()
            _friends.value=da
            simplefriends=da
        }
    }
}