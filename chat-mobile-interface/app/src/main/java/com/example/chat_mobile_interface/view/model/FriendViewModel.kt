package com.example.chat_mobile_interface.view.model

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.chat_mobile_interface.model.UserHandleDto
import com.example.chat_mobile_interface.service.FriendRepo
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class FriendViewModel(val friendRepo: FriendRepo) : ViewModel() {
    private val _friends = MutableLiveData<List<UserHandleDto>>()
    val friendList: LiveData<List<UserHandleDto>> get() = _friends
    val friendFlow: Flow<List<UserHandleDto>> = flow {
        emit(friendRepo.getFriends())
    }

    private val __friends=MutableStateFlow<List<UserHandleDto>>(emptyList())
    val friends2= __friends.asStateFlow()

    fun getFriendsUserHandle(
        id: Int?
    ) {
        viewModelScope.launch {
            val da = friendRepo.getFriends()
            _friends.value = da
            __friends.value=da
            //simplefriends=da
        }
    }
}